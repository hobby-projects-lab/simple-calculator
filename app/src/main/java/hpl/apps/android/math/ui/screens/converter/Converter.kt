package hpl.apps.android.math.ui.screens.converter

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.os.ConfigurationCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.components.button.MathButtonClass
import hpl.apps.android.math.ui.screens.converter.components.ConverterDisplay
import hpl.apps.android.math.ui.screens.converter.components.ConverterKeyPad
import hpl.apps.android.math.ui.screens.converter.components.DimensionGrid
import hpl.apps.android.math.utils.Dimension
import hpl.apps.android.math.utils.MeasurementUnit
import java.util.Locale


private enum class ConverterDestinations(val screenName: String){
    GRID("grid"),
    MAIN("main")
}

@Composable
fun Converter(
    modifier: Modifier = Modifier,
    viewModel: ConverterViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = ConverterDestinations.GRID.screenName,
        modifier = modifier
    ) {
        composable(route = ConverterDestinations.GRID.screenName) {
            DimensionGrid(
                setDestination = {
                    if (viewModel.selectedDimension != it) {
                        viewModel.reset(it)
                    }
                    navController.navigate(ConverterDestinations.MAIN.screenName)
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = ConverterDestinations.MAIN.screenName) {
            ConverterScreen(
                viewModel.selectedDimension,
                viewModel,
                clickHandler = {
                    viewModel.handleClick(
                        it,
                        viewModel.state.value.selectedUnit1,
                        viewModel.state.value.selectedUnit2
                    )
                },
                setUnit1 = viewModel::setUnit1,
                setUnit2 = viewModel::setUnit2,
                swap = viewModel::handleSwap,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
private fun ConverterScreen(
    dimension: Dimension,
    viewModel: ConverterViewModel,
    clickHandler: (MathButtonClass) -> Unit,
    setUnit1: (MeasurementUnit) -> Unit,
    setUnit2: (MeasurementUnit) -> Unit,
    swap: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(dimension.id),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                vertical = dimensionResource(R.dimen.heading_vert_padding),
                horizontal = dimensionResource(R.dimen.heading_horiz_padding)
            )
        )
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            val localConfiguration = LocalConfiguration.current
            val orientation = remember(localConfiguration){ localConfiguration.orientation }
            val localizer = remember(localConfiguration) {
                ConverterLocalizer(
                    ConfigurationCompat.getLocales(localConfiguration).get(0) ?: Locale.getDefault()
                )
            }

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    ConverterKeyPad(
                        clickHandler = clickHandler,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                    ConverterDisplay(
                        dimension = dimension,
                        viewModel = viewModel,
                        localizer = localizer,
                        setUnit1 = setUnit1,
                        setUnit2 = setUnit2,
                        swap = swap,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                }
            }else{
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    ConverterDisplay(
                        dimension = dimension,
                        viewModel = viewModel,
                        localizer = localizer,
                        setUnit1 = setUnit1,
                        setUnit2 = setUnit2,
                        swap = swap,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    ConverterKeyPad(
                        clickHandler = clickHandler,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        }
    }
}