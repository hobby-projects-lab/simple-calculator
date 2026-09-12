package hpl.apps.android.math.ui.screens.calculator

import hpl.apps.android.math.ui.screens.Localizer
import java.util.Locale

class CalculatorLocalizer(locale: Locale): Localizer(locale) {
    var cachedLocalizedExpression = ""
    var cachedLocalizedPreview = ""

    val cachedLocalizedHistory = mutableListOf<String>()
}