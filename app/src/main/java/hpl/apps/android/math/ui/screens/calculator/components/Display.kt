package hpl.apps.android.math.ui.screens.calculator.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.awaitCancellation
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.components.button.OptionButton
import hpl.apps.android.math.ui.components.button.TextButtonClass
import hpl.apps.android.math.ui.screens.calculator.CalculatorLocalizer
import hpl.apps.android.math.ui.screens.calculator.CalculatorViewModel
import hpl.apps.android.math.ui.screens.utils.copyToClipboard
import hpl.apps.android.math.ui.theme.CalculatorTypography

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CalculatorDisplay(
    viewModel: CalculatorViewModel,
    localizer: CalculatorLocalizer,
    modifier: Modifier = Modifier
){
    val localContext = LocalContext.current

    val disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)

    val focusRequester = remember { FocusRequester() }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.calculator_display_field_padding))
    ){
        OptionButton(
            button = if (viewModel.degreeMode.value) TextButtonClass.DEG else TextButtonClass.RAD,
            onClick = { viewModel.handleClick(it, localContext) },
            textStyle = MaterialTheme.typography.labelMedium
        )
        Spacer(
            modifier = Modifier
                .height(dimensionResource(R.dimen.calculator_display_field_small_padding))
        )
        CalculatorHistory(
            viewModel = viewModel,
            localizer = localizer,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        HorizontalDivider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.calculator_input_field_vert_padding))
        ) {
            val expression = if (!viewModel.inputState.value.expressionChanged) {
                localizer.cachedLocalizedExpression.ifEmpty {
                    val localizedExpression = localizer.localizeExpression(
                        viewModel.inputState.value.expression
                    )
                    localizer.cachedLocalizedExpression = localizedExpression
                    localizedExpression
                }
            } else {
                val localizedExpression = localizer.localizeExpression(
                    viewModel.inputState.value.expression
                )
                localizer.cachedLocalizedExpression = localizedExpression
                localizedExpression
            }
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = stringResource(R.string.copy_text),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .clickable{
                        copyToClipboard(localContext, expression)
                    }
                    .padding(dimensionResource(R.dimen.icon_padding))
            )
            Spacer(
                modifier = Modifier
                    .width(dimensionResource(R.dimen.calculator_input_field_horiz_padding))
            )
            InterceptPlatformTextInput(
                interceptor = { _, _ ->
                    awaitCancellation()
                }
            ) {
                LaunchedEffect(Unit) { focusRequester.requestFocus() }
                BasicTextField(
                    value = TextFieldValue(
                        expression,
                        TextRange(
                            localizer.findCursorPositionInLocalizedExpression(
                                expression,
                                viewModel.inputState.value.expression.length,
                                viewModel.inputState.value.cursorPosition
                            )
                        )
                    ),
                    textStyle = CalculatorTypography.input.copy(color = MaterialTheme.colorScheme.onSurface),
                    singleLine = true,
                    onValueChange = {},
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                )
            }
        }
        val preview = if (!viewModel.inputState.value.previewChanged) {
            localizer.cachedLocalizedPreview.ifEmpty {
                val localizedPreview = localizer.localizeExpression(
                    viewModel.inputState.value.preview
                )
                localizer.cachedLocalizedPreview = localizedPreview
                localizedPreview
            }
        } else {
            val localizedPreview = localizer.localizeExpression(
                viewModel.inputState.value.preview
            )
            localizer.cachedLocalizedPreview = localizedPreview
            localizedPreview
        }
        Text(
            text = preview,
            style = CalculatorTypography.inputSmall,
            color = disabledTextColor,
            maxLines = 1,
            overflow = TextOverflow.StartEllipsis,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}