package hpl.apps.android.math.ui.screens.settings

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.GetData
import hpl.apps.android.math.ui.screens.utils.GenericDialog

@Composable
fun DecimalPlacesDialog(
    @StringRes id: Int,
    onDismiss: ()-> Unit,
    viewModel: SettingsViewModel
){
    val context = LocalContext.current

    val state by viewModel.decimalPlaces.collectAsState()
    var input by rememberSaveable { mutableStateOf("") }

    val onDone = {
        val x = input.toIntOrNull()
        if (x == null){
            decimalPlacesConstraintsMessage(context)
        }else{
            viewModel.setDecimalPlaces(x)
            onDismiss()
        }
    }

    GenericDialog(
        id = id,
        onDismissRequest = { onDismiss() },
        content = {
            TextField(
                value = input,
                placeholder = { Text(state.toString()) },
                onValueChange = {
                    if(it.isEmpty()){
                        input  = it
                    }else{
                        val x = it.toIntOrNull()
                        if (x == null || x < GetData.MIN_DECIMAL_PLACES || x > GetData.MAX_DECIMAL_PLACES){
                            decimalPlacesConstraintsMessage(context)
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
        }
    ){
        Button(onClick = { onDone() }) { Text(text = stringResource(R.string.save)) }
    }
}

private fun decimalPlacesConstraintsMessage(context: Context){
    Toast.makeText(
        context,
        context.getString(
            R.string.decimal_places_constraints,
            GetData.MIN_DECIMAL_PLACES,
            GetData.MAX_DECIMAL_PLACES
        ),
        Toast.LENGTH_LONG
    ).show()
}