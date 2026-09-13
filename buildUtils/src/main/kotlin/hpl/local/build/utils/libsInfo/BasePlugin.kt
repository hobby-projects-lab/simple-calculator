package hpl.local.build.utils.libsInfo

internal object BasePlugin {
    const val ID = "com.mikepenz.aboutlibraries.plugin"
    const val TARGET_TASK = "exportLibraryDefinitionsRelease"
    const val OUTPUT_FILE_PATH = "${Data.OUTPUT_FILE_DIR}/libraries_info.json"
}

internal enum class AllowedLicences(val spdxId: String){
    APACHE2("Apache-2.0"),
    MIT("MIT"),
    GPL3("GPL-3.0-only"),
    GPL3PLUS("GPL-3.0-or-later"),
    CC0("CC0-1.0")
}

internal val licencesWithSeparateNoticeFile = listOf(
    AllowedLicences.APACHE2.spdxId
)