package hpl.apps.android.math.ui.screens.about

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import hpl.apps.android.math.AppInfo
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.screens.about.licences.LicenceDialog
import hpl.apps.android.math.ui.screens.utils.FlexGrid
import hpl.apps.android.math.ui.screens.utils.IconItem
import hpl.apps.android.math.utils.icons.IconCollection

@Composable
fun AppInfo(
    openLink: (String)-> Unit,
    goToLicences: ()-> Unit,
    modifier: Modifier = Modifier
){
    val localConfiguration = LocalConfiguration.current

    val orientation = remember(localConfiguration) { localConfiguration.orientation }
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.padding(dimensionResource(R.dimen.app_info_padding))
        ) {
            AppTitle(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Details(
                goToLicences = goToLicences,
                openLink = openLink,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = dimensionResource(R.dimen.app_info_padding))
            )
        }
    }else{
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(dimensionResource(R.dimen.app_info_padding))
        ) {
            AppTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Details(
                openLink = openLink,
                goToLicences = goToLicences,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = dimensionResource(R.dimen.app_info_padding))
            )
        }
    }
}



@Composable
private fun AppTitle(modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        AsyncImage(
            model = R.mipmap.ic_launcher,
            contentDescription = stringResource(R.string.app_icon),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight(0.25f)
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.app_info_padding)))
        Text(
            text = stringResource(R.string.app_full_name),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.app_info_padding)))
        Text(
            text = AppInfo.ID,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.app_info_padding)))
        Text(
            text = stringResource(R.string.version, AppInfo.VERSION_NAME),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.app_info_padding)))
        LicenceButton()
    }
}

@Composable
private fun Details(
    openLink: (String) -> Unit,
    goToLicences: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    color: Color = MaterialTheme.colorScheme.onBackground
){
    FlexGrid(modifier = modifier){
        DetailsItems.entries.forEach {
            IconItem(
                icon = it.icon,
                onClick = {
                    when{
                        it.link != null -> openLink(it.link)
                        it.labelId == R.string.third_party_licences -> goToLicences()
                        else -> {}
                    }
                },
                label = stringResource(it.labelId),
                backgroundColor = backgroundColor,
                color = color
            )
        }
    }
}
private enum class DetailsItems(
    val icon: ImageVector,
    @StringRes val labelId: Int,
    val link: String? = null
){
    SOURCE(
        Icons.Default.Code,
        R.string.source,
        AppInfo.SOURCE_CODE
    ),
    BUG(
        Icons.Default.BugReport,
        R.string.bug_reports,
        AppInfo.BUG_REPORT
    ),
    DONATE(
        IconCollection.Card.icon.vector,
        R.string.donate,
        AppInfo.DONATE
    ),
    TRANSLATE(
        Icons.Default.Translate,
        R.string.translate,
        AppInfo.TRANSLATE
    ),
    LICENCES(
        Icons.AutoMirrored.Default.Article,
        R.string.third_party_licences
    )
}



@Composable
private fun LicenceButton(){
    var displayLicenceText by remember { mutableStateOf(false) }
    Button(
        onClick = { displayLicenceText = !displayLicenceText }
    ) {
        Text(
            text = stringResource(R.string.app_licence),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
    }
    if(displayLicenceText) {
        LicenceDialog(
            id = AppInfo.ID,
            licenceText = AppInfo.LICENCE_TEXT,
            onDismissRequest = { displayLicenceText = !displayLicenceText }
        ){
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.licence_dialog_vert_padding))
            ) {
                Button(onClick = it) { Text(text = stringResource(R.string.ok)) }
            }
        }
    }
}


