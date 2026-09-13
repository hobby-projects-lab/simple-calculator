package hpl.apps.android.math.ui.screens.about.licences

import android.content.Context
import androidx.annotation.RawRes
import com.mikepenz.aboutlibraries.entity.Library
import kotlinx.serialization.json.Json

fun loadLibsLicences(context: Context, @RawRes fileId: Int): Map<String, String> {
    val jsonString = context
        .resources
        .openRawResource(fileId)
        .bufferedReader()
        .use { it.readText() }

    return Json.decodeFromString(jsonString)
}

fun Library.getLibLicenceEntryKey(): String = "${this.artifactVersion}:${this.scm?.url}"