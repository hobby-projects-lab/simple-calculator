package hpl.apps.android.math.ui.screens.converter.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import hpl.apps.android.math.ui.screens.converter.units
import hpl.apps.android.math.ui.screens.utils.FlexGrid
import hpl.apps.android.math.ui.screens.utils.IconItem
import hpl.apps.android.math.utils.Dimension

@Composable
private fun DimensionItem(
    dimension: Dimension,
    onClick: (Dimension)-> Unit,
    modifier: Modifier = Modifier,
){
    IconItem(
        icon = dimension.icon,
        onClick = { onClick(dimension) },
        modifier = modifier,
        label = stringResource(dimension.id)
    )
}

@Composable
fun DimensionGrid(
    setDestination: (Dimension)-> Unit,
    modifier: Modifier = Modifier
){
    FlexGrid(modifier = modifier) {
        units.keys.forEach { dimensionItem ->
            DimensionItem(
                dimensionItem,
                onClick = { setDestination(it) }
            )
        }
    }
}