package hpl.apps.android.math.ui.screens.about.licences

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.ui.compose.android.produceLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.screens.about.AboutDestinations
import hpl.apps.android.math.ui.screens.utils.FlexGrid
import hpl.apps.android.math.ui.screens.utils.IconItem
import hpl.apps.android.math.utils.icons.IconCollection
import hpl.apps.android.math.utils.icons.collection.IconClass
import hpl.apps.android.math.utils.oops


private enum class LicencesDestinations(val screenName: String, @StringRes val id: Int) {
    ICONS(AboutDestinations.ICONS.screenName, R.string.icons),
    FONTS(AboutDestinations.FONTS.screenName, R.string.fonts),
    LIBRARIES(AboutDestinations.LIBRARIES.screenName, R.string.libraries)
}


@Composable
fun Licences(
    screenName: String,
    openLink: (String) -> Unit,
    navigate: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.third_party_licences),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(R.dimen.heading_vert_padding),
                    horizontal = dimensionResource(R.dimen.heading_horiz_padding)
                )
        )
        when(screenName){
            AboutDestinations.ICONS.screenName -> {
                IconsLicences(
                    openLink = { openLink(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.licence_screen_horiz_padding))
                        .weight(1f)
                )
            }

            AboutDestinations.FONTS.screenName -> {
                val fonts by produceLibraries(R.raw.fonts_info)
                val fontsLicences = loadLibsLicences(context, R.raw.fonts_licences)
                LibsLicencesList(
                    libraries = fonts,
                    libsLicences = fontsLicences,
                    openLink = { openLink(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.licence_screen_horiz_padding))
                        .weight(1f)
                )
            }

            AboutDestinations.LIBRARIES.screenName -> {
                val libraries by produceLibraries(R.raw.libraries_info)
                val libsLicences = loadLibsLicences(context, R.raw.libraries_licences)
                val additionalLibsLicences = loadLibsLicences(context, R.raw.additional_libraries_licences)
                LibsLicencesList(
                    libraries = libraries,
                    libsLicences = libsLicences,
                    additionalLibsLicences = additionalLibsLicences,
                    openLink = { openLink(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.licence_screen_horiz_padding))
                        .weight(1f)
                )
            }

            else -> {
                LicenceTypes(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ){ navigate(it) }
            }
        }
    }
}


@Composable
private fun LicenceTypes(
    modifier: Modifier = Modifier,
    setDestination: (String)-> Unit
){
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        LicencesDestinations.entries.forEach {
            Text(
                text = stringResource(it.id),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{ setDestination(it.screenName) }
                    .padding(
                        vertical = dimensionResource(R.dimen.licence_type_item_vert_padding),
                        horizontal = dimensionResource(R.dimen.licence_type_item_horiz_padding)
                    )
            )
            HorizontalDivider()
        }
    }
}


@Composable
private fun LibsLicencesList(
    modifier: Modifier = Modifier,
    libraries: Libs?,
    libsLicences: Map<String, String>,
    additionalLibsLicences: Map<String, String>? = null,
    openLink: (String)-> Unit
){
    var currentLibrary: Library? by remember { mutableStateOf(null) }
    var libLicenceEntryKey: String
    var libLicence: String?

    LibrariesContainer(
        modifier = modifier,
        libraries = libraries,
        divider = { HorizontalDivider() },
        onLibraryClick = {
            currentLibrary = it
            true
        },
        onActionClick = {_, _ -> true }
    )
    currentLibrary?.let {lib ->
        libLicenceEntryKey = lib.getLibLicenceEntryKey()
        libLicence = libsLicences[libLicenceEntryKey]?: additionalLibsLicences?.get(libLicenceEntryKey)
        if(libLicence == null) oops("No Licence text for library ${lib.uniqueId} version ${lib.artifactVersion}")
        LicenceDialog(
            id = lib.uniqueId,
            licenceText = libLicence!!,
            onDismissRequest = { currentLibrary = null }
        ){onDismiss->
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.licence_dialog_vert_padding))
            ) {
                OutlinedButton(
                    onClick = {
                        if(!lib.scm?.url.isNullOrBlank()) openLink(lib.scm?.url!!)
                    }
                ) {
                    Text(text = stringResource(R.string.source))
                }
                OutlinedButton(
                    onClick = {
                        if(!lib.website.isNullOrBlank()) openLink(lib.website!!)
                    }
                ) {
                    Text(text = stringResource(R.string.website))
                }
                Button(onClick = onDismiss) { Text(text = stringResource(R.string.ok)) }
            }
        }
    }
}


@Composable
private fun IconsLicences(
    openLink: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var currentIcon: IconClass? by remember { mutableStateOf(null) }
    FlexGrid(modifier = modifier) {
        IconCollection.entries.forEach {
            IconItem(
                icon = it.icon.vector,
                onClick = { currentIcon = it.icon },
                label = it.icon.id
            )
        }
    }

    currentIcon?.let {icon->
        LicenceDialog(
            id = icon.id,
            licenceText = icon.licenceText,
            onDismissRequest = { currentIcon = null }
        ){onDismiss->
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.licence_dialog_vert_padding))
            ) {
                OutlinedButton(
                    onClick = {
                        openLink(icon.link)
                    }
                ) {
                    Text(text = stringResource(R.string.website))
                }
                Button(onClick = onDismiss) { Text(text = stringResource(R.string.ok)) }
            }
        }
    }
}
