package hpl.apps.android.math.ui.screens.calculator.components.keypad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import hpl.apps.android.math.ui.components.button.MathButtonClass
import hpl.apps.android.math.ui.components.button.LongPressEffectTextButton
import hpl.apps.android.math.ui.components.button.LongPressEffectVectorButton
import hpl.apps.android.math.ui.components.button.TextButton
import hpl.apps.android.math.ui.components.button.TextButtonClass
import hpl.apps.android.math.ui.components.button.VectorButtonClass
import hpl.apps.android.math.ui.screens.Localizer
import hpl.apps.android.math.ui.screens.calculator.CalculatorKeyPadSwitch
import hpl.apps.android.math.ui.screens.calculator.CalculatorLocalizer
import hpl.apps.android.math.ui.screens.calculator.CalculatorViewModel


@Composable
fun CalculatorNumberAndFunctionGrid(
    clickHandler: (MathButtonClass) -> Unit,
    localizer: Localizer,
    keyPadSwitch: CalculatorKeyPadSwitch,
    modifier: Modifier = Modifier
){
    when (keyPadSwitch.controller.intValue) {
        keyPadSwitch.func -> {
            CalculatorFunctionGrid1(
                modifier = modifier,
                switchOther = { keyPadSwitch.switchOther() },
                switchNum = { keyPadSwitch.switchNum() },
                clickHandler = clickHandler,
                localizer = localizer
            )
        }
        keyPadSwitch.funcInv -> {
            CalculatorFunctionGrid2(
                modifier = modifier,
                switchOther = { keyPadSwitch.switchOther() },
                switchNum = { keyPadSwitch.switchNum() },
                clickHandler = clickHandler,
                localizer = localizer
            )
        }
        keyPadSwitch.other -> {
            CalculatorFunctionGrid3(
                modifier = modifier,
                switchNum = { keyPadSwitch.switchNum() },
                clickHandler = clickHandler,
                localizer = localizer
            )
        }
        else -> {
            CalculatorNumberGrid(
                modifier = modifier,
                switchOther = { keyPadSwitch.switchOther() },
                clickHandler = clickHandler
            )
        }
    }
}

@Composable
fun CalculatorKeyPadSpecialKeys(
    clickHandler: (MathButtonClass) -> Unit,
    switchFunc: ()-> Unit,
    switchFuncInv: ()-> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiaryContainer
){
    Column(
        modifier = modifier
            .background(color = backgroundColor)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.QUICK_CURSOR_FORWARD,
                onClick = { clickHandler(it) },
                color = color,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            LongPressEffectTextButton(
                button = TextButtonClass.CURSOR_FORWARD,
                onClick = { clickHandler(it) },
                color = color,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            LongPressEffectTextButton(
                button = TextButtonClass.CURSOR_BACK,
                onClick = { clickHandler(it) },
                color = color,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.QUICK_CURSOR_BACK,
                onClick = { clickHandler(it) },
                color = color,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            TextButton(
                button = TextButtonClass.OPEN_PARENTHESIS,
                onClick = { clickHandler(it) },
                color = color,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.CLOSE_PARENTHESIS,
                onClick = { clickHandler(it) },
                color = color,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.SWITCH_TO_FUNC,
                onClick = { switchFunc() },
                color = color,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.SWITCH_TO_FUNC_INV,
                onClick = { switchFuncInv() },
                color = color,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
    }
}

@Composable
fun CalculatorOperatorsRow(
    clickHandler: (MathButtonClass) -> Unit,
    longClickHandler: (MathButtonClass)-> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer
){
    Row(
        modifier = modifier
            .background(color = backgroundColor)
    ) {
        TextButton(
            button = TextButtonClass.CLEAR,
            onClick = { clickHandler(it) },
            onLongClick = { longClickHandler(it) },
            color = color,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        TextButton(
            button = TextButtonClass.DIV,
            onClick = { clickHandler(it) },
            color = color,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        TextButton(
            button = TextButtonClass.MUL,
            onClick = { clickHandler(it) },
            color = color,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        LongPressEffectVectorButton(
            button = VectorButtonClass.BACKSPACE,
            onClick = { clickHandler(it) },
            color = color,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
    }
}

@Composable
fun CalculatorOperatorsColumn(
    clickHandler: (MathButtonClass) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer
){
    Column(
        modifier = modifier
            .background(color = backgroundColor)
    ) {
        TextButton(
            button = TextButtonClass.SUB,
            color = color,
            onClick = { clickHandler(it) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        TextButton(
            button = TextButtonClass.ADD,
            color = color,
            onClick = { clickHandler(it) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        TextButton(
            button = TextButtonClass.PERCENT,
            color = color,
            onClick = { clickHandler(it) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        TextButton(
            button = TextButtonClass.EQUAL,
            onClick = { clickHandler(it) },
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
fun CalculatorKeyPadMain(
    viewModel: CalculatorViewModel,
    localizer: CalculatorLocalizer,
    horizontal: Boolean,
    modifier: Modifier = Modifier
){
    val localContext = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        CalculatorOperatorsRow(
            clickHandler = { viewModel.handleClick(it, localContext) },
            longClickHandler = { viewModel.handleLongClick(it) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Row(
            modifier = Modifier
                .weight(4f)
        ) {
            if(horizontal){
                CalculatorOperatorsColumn(
                    clickHandler = { viewModel.handleClick(it, localContext) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
                CalculatorNumberAndFunctionGrid(
                    clickHandler = { viewModel.handleClick(it, localContext) },
                    localizer = localizer,
                    keyPadSwitch = viewModel.keyPadSwitch,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(3f)
                )
            }else{
                CalculatorNumberAndFunctionGrid(
                    clickHandler = { viewModel.handleClick(it, localContext) },
                    localizer = localizer,
                    keyPadSwitch = viewModel.keyPadSwitch,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(3f)
                )
                CalculatorOperatorsColumn(
                    clickHandler = { viewModel.handleClick(it, localContext) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
            }
        }
    }
}