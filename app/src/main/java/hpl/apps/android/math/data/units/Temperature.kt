package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.abs_zero
import hpl.apps.android.math.data.units.utils.add
import hpl.apps.android.math.data.units.utils.celsiusToFahrenheit
import hpl.apps.android.math.data.units.utils.celsiusToRankine
import hpl.apps.android.math.data.units.utils.fahrenheitToCelsius
import hpl.apps.android.math.data.units.utils.rankineToCelsius
import hpl.apps.android.math.data.units.utils.subtract
import hpl.apps.android.math.utils.MeasurementUnit

enum class Temperature(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.c,
        "°C",
        {it},
        {it}
    ),
    K(
        R.string.k,
        "K",
        { subtract(it, abs_zero) },
        { add(it, abs_zero) }
    ),
    BASE2(
        R.string.f,
        "°F",
        { celsiusToFahrenheit(it) },
        { fahrenheitToCelsius(it) }
    ),
    RAN(
        R.string.r,
        "°R",
        { celsiusToRankine(it) },
        { rankineToCelsius(it) }
    )
}