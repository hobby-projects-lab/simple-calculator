package hpl.apps.android.math.ui.screens.converter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import hpl.apps.android.math.ui.components.button.MathButtonClass
import hpl.apps.android.math.ui.components.button.LongPressEffectTextButton
import hpl.apps.android.math.ui.components.button.LongPressEffectVectorButton
import hpl.apps.android.math.ui.components.button.TextButton
import hpl.apps.android.math.ui.components.button.TextButtonClass
import hpl.apps.android.math.ui.components.button.VectorButtonClass
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ConverterKeyPad(
    clickHandler: (MathButtonClass)-> Unit,
    modifier: Modifier = Modifier
){
    val configuration = LocalConfiguration.current
    val currentLocale = remember(configuration) { ConfigurationCompat.getLocales(configuration).get(0) ?: Locale.getDefault() }
    val decimalSeparator = remember(configuration) { DecimalFormatSymbols.getInstance(currentLocale).decimalSeparator }
    val numberFormatter = remember(configuration) { NumberFormat.getNumberInstance(currentLocale) }

    val localizedText = remember (configuration){
        mapOf(
            TextButtonClass.ZERO.text to numberFormatter.format(0),
            TextButtonClass.ONE.text to numberFormatter.format(1),
            TextButtonClass.TWO.text to numberFormatter.format(2),
            TextButtonClass.THREE.text to numberFormatter.format(3),
            TextButtonClass.FOUR.text to numberFormatter.format(4),
            TextButtonClass.FIVE.text to numberFormatter.format(5),
            TextButtonClass.SIX.text to numberFormatter.format(6),
            TextButtonClass.SEVEN.text to numberFormatter.format(7),
            TextButtonClass.EIGHT.text to numberFormatter.format(8),
            TextButtonClass.NINE.text to numberFormatter.format(9),
            TextButtonClass.DECIMAL_POINT.text to decimalSeparator.toString(),
        )
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                button = TextButtonClass.QUICK_CURSOR_FORWARD,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            LongPressEffectTextButton(
                button = TextButtonClass.CURSOR_FORWARD,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            LongPressEffectTextButton(
                button = TextButtonClass.CURSOR_BACK,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.QUICK_CURSOR_BACK,
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
                button = TextButtonClass.SEVEN,
                localizedText = localizedText[TextButtonClass.SEVEN.text],
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.EIGHT,
                localizedText = localizedText[TextButtonClass.EIGHT.text],
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.NINE,
                localizedText = localizedText[TextButtonClass.NINE.text],
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            LongPressEffectVectorButton(
                button = VectorButtonClass.BACKSPACE,
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
                button = TextButtonClass.FOUR,
                localizedText = localizedText[TextButtonClass.FOUR.text],
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.FIVE,
                localizedText = localizedText[TextButtonClass.FIVE.text],
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.SIX,
                localizedText = localizedText[TextButtonClass.SIX.text],
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.SIGN,
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
                button = TextButtonClass.ONE,
                localizedText = localizedText[TextButtonClass.ONE.text],
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.TWO,
                localizedText = localizedText[TextButtonClass.TWO.text],
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.THREE,
                localizedText = localizedText[TextButtonClass.THREE.text],
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.DECIMAL_POINT,
                localizedText = localizedText[TextButtonClass.DECIMAL_POINT.text],
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
                button = TextButtonClass.ZERO,
                localizedText = localizedText[TextButtonClass.ZERO.text],
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            TextButton(
                button = TextButtonClass.CLEAR,
                onClick = { clickHandler(it) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(3f)
            )
        }
    }
}