package hpl.apps.android.math.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.theme.CalculatorTypography
import kotlin.time.Duration.Companion.milliseconds


@Composable
fun OptionButton(
    button: TextButtonClass,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    localizedText: String? = null,
    onClick: (MathButtonClass)-> Unit = {}
){
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(
                vertical = dimensionResource(R.dimen.calculator_option_button_vert_padding),
                horizontal = dimensionResource(R.dimen.calculator_option_button_horiz_padding)
            )
            .clickable{ onClick(button) }
    ){
        Text(
            text = localizedText ?: button.buttonText ?: button.text,
            style = textStyle,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}


@Composable
private fun MathButton(
    button: MathButtonClass,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null,
    onClick: (MathButtonClass)-> Unit = {},
    onLongClick: (MathButtonClass)-> Unit = {},
    content: @Composable ()-> Unit
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .combinedClickable(
                interactionSource = interactionSource,
                onClick = { onClick(button) },
                onLongClick = { onLongClick(button) }
            )
            .border(
                width = dimensionResource(R.dimen.calculator_button_border_width),
                color = MaterialTheme.colorScheme.onSurface
            )
    ){
        content()
    }
}

@Composable
fun TextButton(
    button: TextButtonClass,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null,
    localizedText: String? = null,
    textStyle: TextStyle = CalculatorTypography.button,
    color: Color? = null,
    onClick: (MathButtonClass)-> Unit = {},
    onLongClick: (MathButtonClass)-> Unit = {}
){
    MathButton(
        button = button,
        modifier = modifier,
        interactionSource = interactionSource,
        onClick = onClick,
        onLongClick = onLongClick
    ){
        Text(
            text = localizedText ?: button.buttonText ?: button.text,
            style = textStyle,
            color = color ?: Color.Unspecified
        )
    }
}

@Composable
fun VectorButton(
    button: VectorButtonClass,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null,
    color: Color? = null,
    onClick: (MathButtonClass)-> Unit = {},
    onLongClick: (MathButtonClass)-> Unit = {}
){
    MathButton(
        button = button,
        modifier = modifier,
        interactionSource = interactionSource,
        onClick = onClick,
        onLongClick = onLongClick
    ){
        Icon(
            imageVector = button.icon,
            contentDescription = if (button.descriptionId != null) stringResource(button.descriptionId) else null,
            tint = color ?: LocalContentColor.current
        )
    }
}


@Composable
private fun LongPressEffectButton(
    button: MathButtonClass,
    modifier: Modifier = Modifier,
    longPressDelay: Long = 500L,
    stepDelay: Long = 100L,
    onClick: (MathButtonClass) -> Unit = {},
    content: @Composable (Modifier, MutableInteractionSource?) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressedListener by rememberUpdatedState(onClick)

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(longPressDelay.milliseconds)
            while (true) {
                pressedListener(button)
                delay(stepDelay.coerceIn(1L, Long.MAX_VALUE).milliseconds)
            }
        }
    }
    content(modifier,interactionSource)
}

@Composable
fun LongPressEffectVectorButton(
    button: VectorButtonClass,
    modifier: Modifier = Modifier,
    color: Color? = null,
    longPressDelay: Long = 500L,
    stepDelay: Long = 100L,
    onClick: (MathButtonClass) -> Unit = {}
) {
    LongPressEffectButton(
        button = button,
        modifier = modifier,
        longPressDelay = longPressDelay,
        stepDelay = stepDelay,
        onClick = onClick
    ) {mod, interactionSource ->
        VectorButton(
            button = button,
            color = color,
            onClick = onClick,
            interactionSource = interactionSource,
            modifier = mod
        )
    }
}

@Composable
fun LongPressEffectTextButton(
    button: TextButtonClass,
    modifier: Modifier = Modifier,
    localizedText: String? = null,
    textStyle: TextStyle = CalculatorTypography.button,
    color: Color? = null,
    longPressDelay: Long = 500L,
    stepDelay: Long = 100L,
    onClick: (MathButtonClass) -> Unit = {}
) {
    LongPressEffectButton(
        button = button,
        modifier = modifier,
        longPressDelay = longPressDelay,
        stepDelay = stepDelay,
        onClick = onClick
    ) {mod, interactionSource ->
        TextButton(
            button = button,
            localizedText = localizedText,
            textStyle = textStyle,
            color = color,
            onClick = onClick,
            interactionSource = interactionSource,
            modifier = mod
        )
    }
}