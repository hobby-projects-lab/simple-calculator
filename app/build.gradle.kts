import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    //id(libs.plugins.build.utils.get().pluginId)
}

tasks.whenTaskAdded {
    if (name.contains("ArtProfile")) {
        enabled = false
    }
}

android {
    namespace = AppInfo.ID
    compileSdk {
        version = release(AppInfo.COMPILE_SDK)
    }

    defaultConfig {
        applicationId = AppInfo.ID
        minSdk = AppInfo.MIN_SDK
        targetSdk = AppInfo.TARGET_SDK
        versionCode = AppInfo.VERSION_CODE
        versionName = AppInfo.VERSION_NAME

        testInstrumentationRunner = AppInfo.TEST_RUNNER_CLASS

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
            BuildConfigFieldsData.TRANSLATE,
            "\"${AppInfo.TRANSLATE}\""
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
            applicationIdSuffix = AppInfo.ID_DEBUG_SUFFIX
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

java {
    toolchain{
        languageVersion = JavaLanguageVersion.of(21)
    }
}

kotlin {
    compilerOptions { jvmTarget = JvmTarget.JVM_17 }
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
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.obermuhlner.big.math)
    implementation(libs.androidx.datastore.preferences)

    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}


object AppInfo {
    const val ID = "hpl.apps.android.math"
    const val ID_DEBUG_SUFFIX = ".debug"
    const val VERSION_NAME = "1.1.0"
    const val VERSION_CODE = 2

    const val PROJECT_LICENCE_FILE_NAME = "LICENSE"

    const val SOURCE_CODE = "https://cutt.ly/vylapd7n"
    const val BUG_REPORT = "https://cutt.ly/MylaoDeH"
    const val DONATE = "https://cutt.ly/QysgjZJ6"
    const val TRANSLATE = "https://cutt.ly/uyjCGQlq"
    fun getLicenceText(project: Project): String = project
        .rootDir
        .resolve(PROJECT_LICENCE_FILE_NAME)
        .bufferedReader()
        .use{ it.readText() }

    const val COMPILE_SDK = 37
    const val TARGET_SDK = 37
    const val MIN_SDK = 24

    const val TEST_RUNNER_CLASS = "androidx.test.runner.AndroidJUnitRunner"

}

object BuildConfigFieldsData{
    const val SOURCE_CODE = "SOURCE_CODE"
    const val BUG_REPORT = "BUG_REPORT"
    const val DONATE = "DONATE"
    const val TRANSLATE = "TRANSLATE"
    const val LICENCE_TEXT = "LICENCE_TEXT"
    const val TYPE_STRING = "String"
}
