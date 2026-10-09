package hpl.apps.android.math.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import hpl.apps.android.math.R

private val primaryFont = FontFamily(
    Font(R.font.quicksand_regular, FontWeight.Normal),
    Font(R.font.quicksand_medium, FontWeight.Medium),
    Font(R.font.quicksand_light, FontWeight.Light),
    Font(R.font.quicksand_semi_bold, FontWeight.SemiBold),
    Font(R.font.quicksand_bold, FontWeight.Bold)
)

private val defaultTypography = Typography()
val typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(
        fontFamily = primaryFont
    ),
    displayMedium = defaultTypography.displayMedium.copy(
        fontFamily = primaryFont
    ),
    displaySmall = defaultTypography.displaySmall.copy(
        fontFamily = primaryFont
    ),
    headlineLarge = defaultTypography.headlineLarge.copy(
        fontFamily = primaryFont
    ),
    headlineMedium = defaultTypography.headlineMedium.copy(
        fontFamily = primaryFont
    ),
    headlineSmall = defaultTypography.headlineSmall.copy(
        fontFamily = primaryFont
    ),
    titleLarge = defaultTypography.titleLarge.copy(
        fontFamily = primaryFont
    ),
    titleMedium = defaultTypography.titleMedium.copy(
        fontFamily = primaryFont
    ),
    titleSmall = defaultTypography.titleSmall.copy(
        fontFamily = primaryFont
    ),
    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = primaryFont
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(
        fontFamily = primaryFont
    ),
    bodySmall = defaultTypography.bodySmall.copy(
        fontFamily = primaryFont
    ),
    labelLarge = defaultTypography.labelLarge.copy(
        fontFamily = primaryFont
    ),
    labelMedium = defaultTypography.labelMedium.copy(
        fontFamily = primaryFont
    ),
    labelSmall = defaultTypography.labelSmall.copy(
        fontFamily = primaryFont
    )
)


data object CalculatorTypography{
    val button = TextStyle(
        fontFamily = primaryFont,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.Center
    )
    val buttonSmall = TextStyle(
        fontFamily = primaryFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.Center
    )
    val input = TextStyle(
        fontFamily = primaryFont,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    )
    val inputSmall = TextStyle(
        fontFamily = primaryFont,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    )
    val history = TextStyle(
        fontFamily = primaryFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.End
    )
}