import hpl.local.build.utils.appInfo.AppInfo
import hpl.local.build.utils.appInfo.BuildConfigFieldsData
import hpl.local.build.utils.toLiteral

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id(libs.plugins.build.utils.get().pluginId)
}

tasks.withType<Zip> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}
tasks.withType<AbstractArchiveTask> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}


private val signingPass = System.getenv("SIMPLE_CALCULATOR_KEY_PASS")
private val signingKeyPath = System.getenv("SIMPLE_CALCULATOR_KEY_STORE_PATH")
private val signingKeyAlias = System.getenv("SIMPLE_CALCULATOR_KEY_ALIAS")
private val isSigningConfig = ((signingPass != null)&&(signingKeyPath != null)&&(signingKeyAlias != null))

android {
    namespace = AppInfo.ID
    compileSdk {
        version = release(AppInfo.COMPILE_SDK)
    }

    defaultConfig {
        if(isSigningConfig){
            signingConfigs {
                create(AppInfo.VARIANT_RELEASE) {
                    storeFile = file(signingKeyPath)
                    storePassword = signingPass
                    keyAlias = signingKeyAlias
                    keyPassword = signingPass
                }
            }
        }
        applicationId = AppInfo.ID
        minSdk = AppInfo.MIN_SDK
        targetSdk = AppInfo.TARGET_SDK
        versionCode = AppInfo.VERSION_CODE
        versionName = AppInfo.VERSION_NAME

        testInstrumentationRunner = AppInfo.TEST_RUNNER_CLASS

        buildConfigField(
            BuildConfigFieldsData.TYPE_STRING,
            BuildConfigFieldsData.SOURCE_CODE,
            AppInfo.SOURCE_CODE.toLiteral()
        )
        buildConfigField(
            BuildConfigFieldsData.TYPE_STRING,
            BuildConfigFieldsData.BUG_REPORT,
            AppInfo.BUG_REPORT.toLiteral()
        )
        buildConfigField(
            BuildConfigFieldsData.TYPE_STRING,
            BuildConfigFieldsData.DONATE,
            AppInfo.DONATE.toLiteral()
        )
        buildConfigField(
            BuildConfigFieldsData.TYPE_STRING,
            BuildConfigFieldsData.TRANSLATE,
            AppInfo.TRANSLATE.toLiteral()
        )
        buildConfigField(
            BuildConfigFieldsData.TYPE_STRING,
            BuildConfigFieldsData.LICENCE_TEXT,
            AppInfo.getLicenceText(project).toLiteral()
        )
    }

    buildTypes {
        release {
            if(isSigningConfig){
                signingConfig = signingConfigs.getByName(AppInfo.VARIANT_RELEASE)
            }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
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


    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
