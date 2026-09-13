package hpl.apps.android.math

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SwapVerticalCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import hpl.apps.android.math.ui.screens.about.About
import hpl.apps.android.math.ui.screens.calculator.Calculator
import hpl.apps.android.math.ui.screens.converter.Converter
import hpl.apps.android.math.utils.icons.CalculatorVector

@Composable
fun CalculatorApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.CALCULATOR) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = stringResource(it.label)
                        )
                    },
                    label = { Text(stringResource(it.label)) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AnimatedContent(
                targetState = currentDestination,
                transitionSpec = {
                    slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
                },
                label = "Main Screen Transition"
            ){destination ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when(destination){
                        AppDestinations.CONVERTER -> Converter(modifier = Modifier.fillMaxSize())
                        AppDestinations.ABOUT -> About(modifier = Modifier.fillMaxSize())
                        else -> Calculator(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

private enum class AppDestinations(
    @StringRes val label: Int,
    val icon: ImageVector
) {
    CALCULATOR(R.string.calculator, CalculatorVector),
    CONVERTER(R.string.converter, Icons.Filled.SwapVerticalCircle),
    ABOUT(R.string.about, Icons.Filled.Info)
}