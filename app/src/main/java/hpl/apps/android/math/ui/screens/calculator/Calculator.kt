package hpl.apps.android.math.ui.screens.calculator

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.os.ConfigurationCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import hpl.apps.android.math.ui.components.button.MathButtonClass
import hpl.apps.android.math.ui.screens.calculator.components.CalculatorDisplay
import hpl.apps.android.math.ui.screens.calculator.components.keypad.CalculatorKeyPadMain
import hpl.apps.android.math.ui.screens.calculator.components.keypad.CalculatorKeyPadSpecialKeys
import java.util.Locale


@Composable
fun Calculator(
    modifier: Modifier = Modifier,
    calculatorViewModel: CalculatorViewModel = viewModel()
){
    val localContext = LocalContext.current
    CalculatorScreen(
        viewModel = calculatorViewModel,
        clickHandler = {button -> calculatorViewModel.handleClick(button, localContext) },
        switchFunc = { calculatorViewModel.keyPadSwitch.switchFunc() },
        switchFuncInv = { calculatorViewModel.keyPadSwitch.switchFuncInv() },
        modifier = modifier
    )
}

@Composable
private fun CalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel,
    clickHandler: (MathButtonClass) -> Unit,
    switchFunc: ()-> Unit,
    switchFuncInv: ()-> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        val localConfiguration = LocalConfiguration.current
        val localizer = remember(localConfiguration) {
            CalculatorLocalizer(
                ConfigurationCompat.getLocales(localConfiguration).get(0) ?: Locale.getDefault()
            )
        }

        val orientation = remember(localConfiguration) { localConfiguration.orientation }
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = modifier
            ) {
                CalculatorKeyPadMain(
                    viewModel = viewModel,
                    localizer = localizer,
                    horizontal = true,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    CalculatorDisplay(
                        viewModel,
                        localizer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(3f)
                    )
                    CalculatorKeyPadSpecialKeys(
                        clickHandler = clickHandler,
                        switchFunc = switchFunc,
                        switchFuncInv = switchFuncInv,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f)
                    )
                }
            }
        } else {
            Column(
                modifier = modifier
            ) {
                CalculatorDisplay(
                    viewModel,
                    localizer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5f)
                )
                CalculatorKeyPadSpecialKeys(
                    clickHandler = clickHandler,
                    switchFunc = switchFunc,
                    switchFuncInv = switchFuncInv,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                )
                CalculatorKeyPadMain(
                    viewModel = viewModel,
                    localizer = localizer,
                    horizontal = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5f)
                )
            }
        }
    }
}