package hpl.apps.android.math.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import hpl.apps.android.math.R

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
){
    var selected: Boolean by rememberSaveable { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
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
        Text(
            text = stringResource(R.string.precision),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { selected = true }
                .padding(
                    vertical = dimensionResource(R.dimen.licence_type_item_vert_padding),
                    horizontal = dimensionResource(R.dimen.licence_type_item_horiz_padding)
                )
        )
        HorizontalDivider()
    }

    if(selected){
        Dialog(onDismissRequest = { selected = false }) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(MaterialTheme.shapes.small)
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(
                        horizontal = dimensionResource(R.dimen.precision_dialog_horiz_padding),
                        vertical = dimensionResource(R.dimen.precision_dialog_vert_padding),
                    )
            ) {
                val state by viewModel.precision.collectAsState()
                var input by rememberSaveable { mutableStateOf("") }
                TextField(
                    value = input,
                    label = { Text(text = stringResource(R.string.precision)) },
                    placeholder = { Text(state.toString()) },
                    onValueChange = {
                        try{ input = if(it.isEmpty()) it else it.toInt().toString() }catch(_: Exception){}
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        showKeyboardOnFocus = true,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            try{
                                val x = input.toInt()
                                viewModel.setPrecision(x)
                                selected = false
                            }catch(_: Exception){}
                        }
                    )
                )
            }
        }
    }
}