package hpl.apps.android.math.ui.screens.calculator.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.screens.calculator.CalculatorLocalizer
import hpl.apps.android.math.ui.screens.calculator.CalculatorViewModel
import hpl.apps.android.math.ui.theme.CalculatorTypography

@Composable
fun CalculatorHistory(
    viewModel: CalculatorViewModel,
    localizer: CalculatorLocalizer,
    modifier: Modifier = Modifier
) {
    val localContext = LocalContext.current
    val lazyListState = rememberLazyListState()

    LaunchedEffect(viewModel.historySize.intValue) {
        if (viewModel.historySize.intValue > 0) {
            lazyListState.scrollToItem(viewModel.historySize.intValue - 1)
        }
    }
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        if (localizer.cachedLocalizedHistory.size > viewModel.historySize.intValue) {
            localizer.cachedLocalizedHistory.clear()
        }
        items(
            viewModel.historySize.intValue,
            key = { index -> viewModel.history.getOrNull(index)?.id ?: index }
        ) {
            Text(
                text = if (it in localizer.cachedLocalizedHistory.indices) {
                    localizer.cachedLocalizedHistory[it]
                } else {
                    val localizedExpression =
                        localizer.localizeExpression(viewModel.history[it].expression)
                    localizer.cachedLocalizedHistory.add(localizedExpression)
                    localizedExpression
                },
                style = CalculatorTypography.history,
                maxLines = 1,
                overflow = TextOverflow.StartEllipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.setExpression(viewModel.history[it].expression, localContext)
                    }
                    .padding(vertical = dimensionResource(R.dimen.calculator_history_item_vert_padding))
            )
        }
    }
}