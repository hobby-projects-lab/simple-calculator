package hpl.apps.android.math.previews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hpl.apps.android.math.ui.screens.about.AboutDestinations
import hpl.apps.android.math.ui.screens.about.licences.Licences


@Preview(showBackground = true)
@Composable
private fun LicencesScreenPreview(){
    Licences(
        AboutDestinations.ICONS.screenName,
        {_->},
        {_->},
        modifier = Modifier.fillMaxSize()
    )
}