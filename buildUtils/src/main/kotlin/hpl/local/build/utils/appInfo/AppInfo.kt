package hpl.local.build.utils.appInfo

import hpl.local.build.utils.Utils
import org.gradle.api.Project

object AppInfo {
    const val ID = "hpl.apps.android.math"
    const val ID_DEBUG_SUFFIX = ".debug"
    const val VERSION_NAME = "1.0.1"
    const val VERSION_CODE = 1

    const val SOURCE_CODE = "https://cutt.ly/vylapd7n"
    const val BUG_REPORT = "https://cutt.ly/MylaoDeH"
    const val DONATE = "https://cutt.ly/QysgjZJ6"
    const val TRANSLATE = "https://cutt.ly/uyjCGQlq"
    fun getLicenceText(project: Project): String = project
        .rootDir
        .resolve(Utils.PROJECT_LICENCE_FILE_NAME)
        .bufferedReader()
        .use{ it.readText() }

    const val COMPILE_SDK = 37
    const val TARGET_SDK = 37
    const val MIN_SDK = 23

    const val VARIANT_RELEASE = "release"

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