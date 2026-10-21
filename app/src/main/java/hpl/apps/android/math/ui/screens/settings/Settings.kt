package hpl.apps.android.math.ui.screens.settings

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.GetData

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
){
    val context = LocalContext.current
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

                val onDone = {
                    val x = input.toIntOrNull()
                    if (x == null){
                        precisionConstraintsMessage(context)
                    }else{
                        viewModel.setPrecision(x)
                        selected = false
                    }
                }

                Text(
                    text = stringResource(R.string.precision),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.precision_dialog_vert_padding)))
                HorizontalDivider()
                Text(
                    text = stringResource(R.string.precision_description),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.precision_dialog_vert_padding)))
                TextField(
                    value = input,
                    placeholder = { Text(state.toString()) },
                    onValueChange = {
                        if(it.isEmpty()){
                            input  = it
                        }else{
                            val x = it.toIntOrNull()
                            if (x == null || x < GetData.MIN_PRECISION || x > GetData.MAX_PRECISION){
                                precisionConstraintsMessage(context)
                            }else{
                                input = it
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        showKeyboardOnFocus = true,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { onDone() }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.precision_dialog_vert_padding)))
                Button(onClick = { onDone() }) { Text(text = stringResource(R.string.save)) }
            }
        }
    }
}

private fun precisionConstraintsMessage(context: Context){
    Toast.makeText(
        context,
        context.getString(
            R.string.precision_constraints,
            GetData.MIN_PRECISION,
            GetData.MAX_PRECISION
        ),
        Toast.LENGTH_LONG
    ).show()
}