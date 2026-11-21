package hpl.apps.android.math.ui.screens.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.screens.utils.DISABLED_ALPHA


@Composable
private fun SettingsItem(
    @StringRes id: Int,
    @StringRes descriptionId: Int,
    value: String,
    selectSetting: (Int)-> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .padding(dimensionResource(R.dimen.settings_item_padding))
            .clickable{ selectSetting(id) }
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.settings_item_padding)))
            Text(
                text = stringResource(descriptionId),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(DISABLED_ALPHA)
            )
        }
        Button(onClick = { selectSetting(id) }) { Text(text = value) }
    }
}

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
){
    val state by viewModel.decimalPlaces.collectAsState()
    val scrollState = rememberScrollState()

    var selected: Int? by rememberSaveable { mutableStateOf(null) }
    val selectSetting = {id: Int -> selected = id}
    val unselectSetting = { selected = null }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(scrollState)
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(R.dimen.heading_vert_padding),
                    horizontal = dimensionResource(R.dimen.heading_horiz_padding)
                )
        )
        SettingsItem(
            id = R.string.decimal_places,
            descriptionId = R.string.decimal_places_description,
            value = state.toString(),
            selectSetting = {selectSetting(it)},
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalDivider()
    }

    if(selected == R.string.decimal_places){
        DecimalPlacesDialog(
            id = R.string.decimal_places,
            onDismiss = { unselectSetting() },
            viewModel = viewModel
        )
    }
}