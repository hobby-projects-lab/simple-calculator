import com.mikepenz.aboutlibraries.plugin.DuplicateMode
import com.mikepenz.aboutlibraries.plugin.DuplicateRule
import com.mikepenz.aboutlibraries.plugin.StrictMode

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.aboutLibraries.plugin)
}

tasks.whenTaskAdded {
    if (name.contains("ArtProfile") || name.contains("prepareLibraryDefinitions")) {
        enabled = false
    }
}

aboutLibraries{
    offlineMode = false
    collect{
        fetchRemoteLicense = true
        fetchRemoteFunding = false
        includePlatform = false
        filterVariants.addAll(AppInfo.VARIANT_RELEASE)
        gitHubApiToken = System.getenv("GITHUB_API_TOKEN")
    }
    export{
        prettyPrint = true
    }
    license{
        strictMode = StrictMode.FAIL
        allowedLicenses.addAll(
            AllowedLicences.APACHE2.spdxId,
            AllowedLicences.MIT.spdxId,
            AllowedLicences.GPL3.spdxId,
            AllowedLicences.GPL3PLUS.spdxId,
            AllowedLicences.CC0.spdxId,
            AllowedLicences.BSD3.spdxId
        )
    }
    library{
        duplicationMode = DuplicateMode.MERGE
        duplicationRule = DuplicateRule.EXACT
    }
}

android {
    namespace = project.property(AppInfo.APP_ID_KEY).toString()
    compileSdk {
        version = release(project.property(AppInfo.COMPILE_SDK_KEY).toString().toInt())
    }

    defaultConfig {
        applicationId = project.property(AppInfo.APP_ID_KEY).toString()
        minSdk = project.property(AppInfo.MIN_SDK_KEY).toString().toInt()
        targetSdk = project.property(AppInfo.TARGET_SDK_KEY).toString().toInt()
        versionCode = project.property(AppInfo.VERSION_CODE_KEY).toString().toInt()
        versionName = project.property(AppInfo.VERSION_NAME_KEY).toString()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            BuildConfigFieldsData.TYPE_STRING,
            BuildConfigFieldsData.SOURCE_CODE,
            "\"${AppInfo.SOURCE_CODE}\""
        )
        buildConfigField(
            BuildConfigFieldsData.TYPE_STRING,
            BuildConfigFieldsData.BUG_REPORT,
            "\"${AppInfo.BUG_REPORT}\""
        )
        buildConfigField(
            BuildConfigFieldsData.TYPE_STRING,
            BuildConfigFieldsData.DONATE,
            "\"${AppInfo.DONATE}\""
        )
        buildConfigField(
            BuildConfigFieldsData.TYPE_STRING,
            BuildConfigFieldsData.LICENCE_TEXT,
            "\"\"\"\n${AppInfo.getLicenceText(project)}\"\"\""
        )
    }

    buildTypes {
        release {
            vcsInfo.include = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = AppInfo.VARIANT_DEBUG
            versionNameSuffix = "-${AppInfo.VARIANT_DEBUG}"
            isDebuggable = true
        }
    }
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    jvmToolchain(21)
}

dependencyLocking {
    lockAllConfigurations()
}
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)
    implementation(libs.notkamui.keval)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.aboutlibraries.compose.m3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.coil.compose)
    implementation(libs.obermuhlner.big.math)
    implementation(libs.androidx.datastore.preferences)

    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}


private object AppInfo {
    const val VARIANT_RELEASE = "release"
    const val VARIANT_DEBUG = "debug"

    const val APP_ID_KEY = "APP_ID"
    const val VERSION_NAME_KEY = "VERSION_NAME"
    const val VERSION_CODE_KEY = "VERSION_CODE"
    const val MIN_SDK_KEY = "MIN_SDK"
    const val TARGET_SDK_KEY = "TARGET_SDK"
    const val COMPILE_SDK_KEY = "COMPILE_SDK"

    const val PROJECT_LICENCE_FILE_NAME = "LICENSE"

    const val SOURCE_CODE = "https://cutt.ly/vylapd7n"
    const val BUG_REPORT = "https://cutt.ly/MylaoDeH"
    const val DONATE = "https://cutt.ly/QysgjZJ6"
    fun getLicenceText(project: Project): String = project
        .rootDir
        .resolve(PROJECT_LICENCE_FILE_NAME)
        .bufferedReader()
        .use{ it.readText() }

}

private object BuildConfigFieldsData{
    const val SOURCE_CODE = "SOURCE_CODE"
    const val BUG_REPORT = "BUG_REPORT"
    const val DONATE = "DONATE"
    const val LICENCE_TEXT = "LICENCE_TEXT"
    const val TYPE_STRING = "String"
}

private enum class AllowedLicences(val spdxId: String){
    APACHE2("Apache-2.0"),
    MIT("MIT"),
    GPL3("GPL-3.0-only"),
    GPL3PLUS("GPL-3.0-or-later"),
    CC0("CC0-1.0"),
    BSD3("BSD-3-Clause")
}
