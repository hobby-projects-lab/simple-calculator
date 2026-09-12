package hpl.apps.android.math.previews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hpl.apps.android.math.ui.screens.about.AppInfo

@Preview(showBackground = true)
@Composable
private fun AppInfoPreview(){
    AppInfo(
        openLink = {},
        goToLicences = {},
        modifier = Modifier.fillMaxSize()
    )
}