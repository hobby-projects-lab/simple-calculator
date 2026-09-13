package hpl.local.build.utils.libsInfo

import com.mikepenz.aboutlibraries.plugin.AboutLibrariesExtension
import com.mikepenz.aboutlibraries.plugin.DuplicateMode
import com.mikepenz.aboutlibraries.plugin.DuplicateRule
import com.mikepenz.aboutlibraries.plugin.StrictMode
import org.gradle.api.Project

internal fun applyBuildUtils(project: Project) {
    project.plugins.apply(BasePlugin.ID)
    val baseExtension = project.extensions.getByType(AboutLibrariesExtension::class.java)

    baseExtension.offlineMode.set(true)
    baseExtension.collect.fetchRemoteLicense.set(false)
    baseExtension.collect.fetchRemoteFunding.set(false)
    baseExtension.collect.includePlatform.set(false)
    baseExtension.collect.filterVariants.addAll(Data.VARIANT_RELEASE)

    baseExtension.export.prettyPrint.set(true)
    baseExtension.export.outputFile.set(project.file(BasePlugin.OUTPUT_FILE_PATH))

    baseExtension.license.strictMode.set(StrictMode.FAIL)
    baseExtension.license.allowedLicenses.addAll(
        AllowedLicences.APACHE2.spdxId,
        AllowedLicences.MIT.spdxId,
        AllowedLicences.GPL3.spdxId,
        AllowedLicences.GPL3PLUS.spdxId,
        AllowedLicences.CC0.spdxId
    )

    baseExtension.library.duplicationMode.set(DuplicateMode.MERGE)
    baseExtension.library.duplicationRule.set(DuplicateRule.EXACT)
    project.tasks.register("createLibsInfo") {
        group = "Isolated"
        description = "Creates json files with information about dependencies and their licences"
        dependsOn(project.tasks.named(BasePlugin.TARGET_TASK))

        doLast {
            Data.createLibsLicences(project, System.getenv("GITHUB_API_TOKEN"))
        }
    }
}
