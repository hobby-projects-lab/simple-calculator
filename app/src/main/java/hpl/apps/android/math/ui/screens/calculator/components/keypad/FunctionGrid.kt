package hpl.apps.android.math.ui.screens.calculator.components.keypad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import hpl.apps.android.math.ui.components.button.MathButtonClass
import hpl.apps.android.math.ui.components.button.TextButton
import hpl.apps.android.math.ui.components.button.TextButtonClass
import hpl.apps.android.math.ui.screens.Localizer
import hpl.apps.android.math.ui.theme.CalculatorTypography

@Composable
fun CalculatorFunctionGrid1(
    switchOther: () -> Unit,
    switchNum: ()-> Unit,
    clickHandler: (MathButtonClass) -> Unit,
    localizer: Localizer,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.COS,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.SIN,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.TAN,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.LN,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.LOG,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.LOG2,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.COSH,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.SINH,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.TANH,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.SWITCH_TO_OTHER_MATH,
                onClick = { switchOther() },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.LOG_X,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.SWITCH_TO_NUM,
                localizedText = localizer.localizeExpression(
                    TextButtonClass.SWITCH_TO_NUM.buttonText ?: TextButtonClass.SWITCH_TO_NUM.text,
                    false
                ),
                onClick = { switchNum() },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
    }
}

@Composable
fun CalculatorFunctionGrid2(
    switchOther: () -> Unit,
    switchNum: ()-> Unit,
    clickHandler: (MathButtonClass) -> Unit,
    localizer: Localizer,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.ARCCOS,
                textStyle = CalculatorTypography.buttonSmall,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.ARCSIN,
                textStyle = CalculatorTypography.buttonSmall,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.ARCTAN,
                textStyle = CalculatorTypography.buttonSmall,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.EXP,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.POW10,
                localizedText = localizer.localizeExpression(
                    TextButtonClass.POW10.buttonText ?: TextButtonClass.POW10.text
                ),
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.POW2,
                localizedText = localizer.localizeExpression(
                    TextButtonClass.POW2.buttonText ?: TextButtonClass.POW2.text
                ),
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.ARCCOSH,
                textStyle = CalculatorTypography.buttonSmall,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.ARCSINH,
                textStyle = CalculatorTypography.buttonSmall,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.ARCTANH,
                textStyle = CalculatorTypography.buttonSmall,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.SWITCH_TO_OTHER_MATH,
                onClick = { switchOther() },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.POW,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.SWITCH_TO_NUM,
                localizedText = localizer.localizeExpression(
                    TextButtonClass.SWITCH_TO_NUM.buttonText ?: TextButtonClass.SWITCH_TO_NUM.text,
                    false
                ),
                onClick = { switchNum() },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
    }
}

@Composable
fun CalculatorFunctionGrid3(
    switchNum: ()-> Unit,
    clickHandler: (MathButtonClass) -> Unit,
    localizer: Localizer,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.MOD,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.FACT,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.SQRT,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.SCI_NOTATION,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.ROOT_3,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.ROOT,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.PI,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.E,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.INV,
                localizedText = localizer.localizeExpression(
                    TextButtonClass.INV.buttonText ?: TextButtonClass.INV.text, false
                ),
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.SWITCH_TO_NUM,
                localizedText = localizer.localizeExpression(
                    TextButtonClass.SWITCH_TO_NUM.buttonText ?: TextButtonClass.SWITCH_TO_NUM.text,
                    false
                ),
                onClick = { switchNum() },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.ABS,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.SQUARE,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
    }
}