package hpl.apps.android.math.ui.screens.converter.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.SwapVerticalCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.awaitCancellation
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.screens.converter.ConverterLocalizer
import hpl.apps.android.math.ui.screens.converter.ConverterViewModel
import hpl.apps.android.math.ui.screens.converter.units
import hpl.apps.android.math.ui.screens.utils.copyToClipboard
import hpl.apps.android.math.ui.theme.CalculatorShapes
import hpl.apps.android.math.ui.theme.CalculatorTypography
import hpl.apps.android.math.utils.Dimension
import hpl.apps.android.math.utils.MeasurementUnit

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ConverterDisplay(
    dimension: Dimension,
    viewModel: ConverterViewModel,
    localizer: ConverterLocalizer,
    setUnit1: (MeasurementUnit)->Unit,
    setUnit2: (MeasurementUnit)-> Unit,
    swap: ()-> Unit,
    modifier: Modifier = Modifier
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        val localContext = LocalContext.current

        val focusRequester = remember { FocusRequester() }

        Column(modifier = Modifier.fillMaxSize()){
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = dimensionResource(R.dimen.converter_display_field_border_width),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    .padding(dimensionResource(R.dimen.converter_display_field_padding))
                    .weight(1f)
            ) {
                UnitsList(
                    selectedUnitId = viewModel.state.value.selectedUnit1.id,
                    setUnit = { setUnit1(it) },
                    dimension = dimension
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(R.dimen.converter_input_field_padding))
                ) {
                    InterceptPlatformTextInput(
                        interceptor = { _, _ ->
                            awaitCancellation()
                        }
                    ) {
                        val expression = if (!viewModel.state.value.expressionChanged) {
                            localizer.cachedLocalizedExpression.ifEmpty {
                                val localizedExpression = localizer.localizeExpression(
                                    viewModel.state.value.expression
                                )
                                localizer.cachedLocalizedExpression = localizedExpression
                                localizedExpression
                            }
                        } else {
                            val localizedExpression = localizer.localizeExpression(
                                viewModel.state.value.expression
                            )
                            localizer.cachedLocalizedExpression = localizedExpression
                            localizedExpression
                        }

                        LaunchedEffect(Unit) { focusRequester.requestFocus() }
                        BasicTextField(
                            value = TextFieldValue(
                                expression,
                                TextRange(
                                    localizer.findCursorPositionInLocalizedExpression(
                                        expression,
                                        viewModel.state.value.expression.length,
                                        viewModel.state.value.cursorPosition
                                    )
                                )
                            ),
                            textStyle = CalculatorTypography.inputSmall.copy(color = MaterialTheme.colorScheme.onSurface),
                            singleLine = true,
                            onValueChange = {},
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester)
                        )
                    }
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.converter_input_field_horiz_padding)))
                    Text(
                        text = viewModel.state.value.selectedUnit1.symbol,
                        style = CalculatorTypography.inputSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(color = MaterialTheme.colorScheme.secondaryContainer)
                            .padding(dimensionResource(R.dimen.converter_screen_unit_label_horiz_padding))
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = dimensionResource(R.dimen.converter_display_field_border_width),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    .padding(dimensionResource(R.dimen.converter_display_field_padding))
                    .weight(1f)
            ) {
                UnitsList(
                    selectedUnitId = viewModel.state.value.selectedUnit2.id,
                    setUnit = { setUnit2(it) },
                    dimension = dimension
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(R.dimen.converter_input_field_padding))
                ) {
                    val expression = localizer.localizeExpression(viewModel.state.value.result)
                    Icon(
                        Icons.Default.ContentCopy,
                        contentDescription = stringResource(R.string.copy_text),
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .clickable{
                                copyToClipboard(localContext, expression)
                            }
                            .padding(dimensionResource(R.dimen.icon_padding))
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.converter_input_field_horiz_padding)))
                    Text(
                        text = expression,
                        style = CalculatorTypography.inputSmall,
                        maxLines = 1,
                        overflow = TextOverflow.StartEllipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.converter_input_field_horiz_padding)))
                    Text(
                        text = viewModel.state.value.selectedUnit2.symbol,
                        style = CalculatorTypography.inputSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(color = MaterialTheme.colorScheme.secondaryContainer)
                            .padding(dimensionResource(R.dimen.converter_screen_unit_label_horiz_padding))
                    )
                }
            }
        }
        Icon(
            Icons.Filled.SwapVerticalCircle,
            contentDescription = stringResource(R.string.swap),
            modifier = Modifier
                .size(dimensionResource(R.dimen.swap_icon_size))
                .clip(CalculatorShapes.round)
                .background(color = MaterialTheme.colorScheme.background)
                .clickable{ swap() }
        )
    }
}



@Composable
private fun UnitsList(
    @StringRes selectedUnitId: Int,
    setUnit: (MeasurementUnit) -> Unit,
    dimension: Dimension
){
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .clickable { expanded = !expanded }
        ) {
            Text(
                text = stringResource(selectedUnitId),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = dimensionResource(R.dimen.converter_screen_unit_label_horiz_padding))
            )
            Icon(
                if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                contentDescription = stringResource(R.string.more_options)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            units[dimension]?.forEach { (_, unit) ->
                DropdownMenuItem(
                    text = { Text(text = "${stringResource(unit.id)} (${unit.symbol})") },
                    onClick = {
                        setUnit(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}