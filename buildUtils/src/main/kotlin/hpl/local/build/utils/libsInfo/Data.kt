package hpl.local.build.utils.libsInfo

import com.mikepenz.aboutlibraries.Libs
import hpl.local.build.utils.appInfo.AppInfo
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import org.gradle.api.Project


internal object Data {
    const val VARIANT_RELEASE = AppInfo.VARIANT_RELEASE

    private const val RESOURCE_DIR = "build/generated/libsInfo/res"
    const val OUTPUT_FILE_DIR = "$RESOURCE_DIR/raw"
    const val OUTPUT_FILE = "$OUTPUT_FILE_DIR/libraries_licences.json"
    const val FAILS_FILE = "$OUTPUT_FILE_DIR/fails.json"

    fun createLibsLicences(project: Project, token: String?){
        val libraries = Libs
            .Builder()
            .withJson(project.file(BasePlugin.OUTPUT_FILE_PATH).readText())
            .build()

        var licenceContent: String?
        var noticeContent: String? = null

        val libsLicences: MutableMap<String, String> = mutableMapOf()
        val fails: MutableMap<String, MutableSet<String>> = mutableMapOf()

        val includeInLibsLicences: (String, String)-> Unit = {key, value ->
            if(!libsLicences.containsKey(key)) libsLicences[key] = value
        }
        val includeInFails: (String, String)-> Unit = {key, value ->
            if(fails.containsKey(key)) fails[key]!!.add(value) else fails[key] = mutableSetOf(value)
        }

        var url: String
        var versionName: String
        var libLicenceEntryKey: String

        for(lib in libraries.libraries){
            if(lib.scm?.url == null || lib.artifactVersion == null){
                val x = "Missing url or version. "+
                        "url = ${lib.scm?.url}, "+
                        "version = ${lib.artifactVersion}"
                includeInFails(lib.uniqueId, x)
                continue
            }
            url = lib.scm?.url!!
            versionName = lib.artifactVersion!!
            libLicenceEntryKey = "$versionName:$url"

            if(libsLicences.containsKey(libLicenceEntryKey)) continue

            println("Fetching licence for ${lib.uniqueId}:${lib.artifactVersion}...")
            licenceContent = Utils.fetchLicenceText(url, versionName, token)
            if(licenceContent == null){
                includeInFails(url, versionName)
                println("Fetch of licence for ${lib.uniqueId}:${lib.artifactVersion} failed.")
                continue
            }
            for (licence in lib.licenses){
                if (licencesWithSeparateNoticeFile.contains(licence.spdxId)){
                    noticeContent = Utils.fetchNoticeText(url, versionName, token)
                    break
                }
            }
            includeInLibsLicences(
                libLicenceEntryKey,
                (if(noticeContent != null) "$noticeContent\n\n---------\n\n" else "")+licenceContent
            )
            println("Fetch of licence for ${lib.uniqueId}:${lib.artifactVersion} successful!")
        }

        val libsLicencesFile = project.file(OUTPUT_FILE)
        libsLicencesFile.parentFile.mkdirs()
        if(!libsLicencesFile.exists()) libsLicencesFile.createNewFile()
        var jsonString: String = Json{ prettyPrint = true }.encodeToString(libsLicences)
        libsLicencesFile.writeText(jsonString)


        val failsFile = project.file(FAILS_FILE)
        failsFile.parentFile.mkdirs()
        if(!failsFile.exists()) failsFile.createNewFile()
        jsonString = Json{ prettyPrint = true }.encodeToString(fails)
        failsFile.writeText(jsonString)

    }
}


private object Utils{
    private const val LICENCE_FILE_NAME = "LICENSE"
    private const val NOTICE_FILE_NAME = "NOTICE"

    private fun getTagsList(versionName: String): List<String> = listOf("v$versionName", versionName)
    private fun getPathsList(): List<String> = listOf("", "license/")
    private fun getExtensionsList(): List<String> = listOf("", ".txt")

    private val githubRegex = Regex("^(https://)?(www\\.)?github\\.com/([^/]+)/([^/]+)/?$")
    private const val GITHUB_API_VERSION = "2026-03-10"
    private const val REQUEST_APP_NAME = "KotlinApp"


    private val httpClient = OkHttpClient()
    private fun getGitHubUrl(owner: String, repo: String): String = "https://api.github.com/repos/$owner/$repo"
    private fun getGitHubTagUrl(
        owner: String,
        repo: String,
        tag: String
    ): String = "${getGitHubUrl(owner, repo)}/git/ref/tags/$tag"
    private fun getGitHubFileUrl(
        owner: String,
        repo: String,
        filePath: String,
        tag: String
    ): String = "${getGitHubUrl(owner, repo)}/contents/$filePath?ref=$tag"
    private fun <T> makeGithubGetRequest(
        url: String,
        mediaType: String,
        token: String? = null,
        callBack: (Request)->T
    ): T{
        val request = Request.Builder()
            .url(url)
            .header("Accept", "application/vnd.github$mediaType")
            .header("X-GitHub-Api-Version", GITHUB_API_VERSION)
            .header("User-Agent", REQUEST_APP_NAME)
            .apply { token?.let { addHeader("Authorization", "Bearer $it") } }
            .build()

        return callBack(request)
    }
    private fun checkGitHubTag(owner: String, repo: String, tag: String, token: String? = null): Boolean {
        return makeGithubGetRequest(
            getGitHubTagUrl(owner, repo, tag),
            "+json",
            token
        ){
            val response = httpClient.newCall(it).execute()
            response.close()
            response.code == 200
        }
    }
    private fun getGithubFileContent(
        owner: String,
        repo: String,
        filePath: String,
        tag: String,
        token: String? = null
    ): String?{
        return makeGithubGetRequest(
            getGitHubFileUrl(owner, repo, filePath, tag),
            ".raw",
            token
        ){
            val response = httpClient.newCall(it).execute()
            val text = response.body.string()
            if (response.code == 200) text else null
        }
    }


    private fun getRepoInfo(url: String): RepoInfo?{
        val match = githubRegex.find(url)
        val owner = match?.groupValues[3]
        val name = match?.groupValues[4]?.removeSuffix(".git")
        return if (owner == null || name == null) null else RepoInfo(owner, name)
    }
    private fun getFileContent(
        repoInfo: RepoInfo,
        filePath: String,
        tag: String,
        token: String?
    ): String?{
        return getGithubFileContent(repoInfo.owner, repoInfo.name, filePath, tag, token)
    }
    private fun checkTag(repoInfo: RepoInfo, tag: String, token: String?): Boolean{
        return checkGitHubTag(repoInfo.owner, repoInfo.name, tag, token)
    }




    private fun fetchRemoteFileContent(url: String, versionName: String, fileName: String, token: String?): String? {
        val repoInfo = getRepoInfo(url)?: return null

        val tags = getTagsList(versionName)
        val paths = getPathsList()
        val extensions = getExtensionsList()

        var fileContent: String? = null
        var tag: String? = null

        for (t in tags){
            if(checkTag(repoInfo, t, token)){
                tag = t
                break
            }
        }
        if(tag == null) return null

        fileFetchLoop@ for (path in paths){
            for (ext in extensions){
                fileContent = getFileContent(repoInfo, "$path${fileName}${ext}", tag, token)
                if (fileContent != null) break@fileFetchLoop
            }
        }
        if(fileContent == null) return null

        return fileContent
    }



    fun fetchLicenceText(url: String, versionName: String, token: String?): String?{
        return fetchRemoteFileContent(url, versionName, LICENCE_FILE_NAME, token)
    }
    fun fetchNoticeText(url: String, versionName: String, token: String?): String?{
        return fetchRemoteFileContent(url, versionName, NOTICE_FILE_NAME, token)
    }

}

private data class RepoInfo(
    val owner: String,
    val name: String
)