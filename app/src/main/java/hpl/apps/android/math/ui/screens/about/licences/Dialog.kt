package hpl.apps.android.math.ui.screens.about.licences

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import hpl.apps.android.math.R

@Composable
fun LicenceDialog(
    id: String,
    licenceText: String,
    onDismissRequest: ()-> Unit,
    footer: @Composable (()-> Unit)-> Unit
){
    val scrollState = rememberScrollState()

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
                .clip(MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colorScheme.background)
                .padding(
                    horizontal = dimensionResource(R.dimen.licence_dialog_horiz_padding),
                    vertical = dimensionResource(R.dimen.licence_dialog_vert_padding),
                )
        ) {
            Text(
                text = id,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.licence_dialog_vert_padding))
            )
            HorizontalDivider()
            Text(
                text = licenceText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(vertical = dimensionResource(R.dimen.licence_dialog_vert_padding))
            )
            HorizontalDivider()
            footer(onDismissRequest)
        }
    }
}