package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.BILLION
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.utils.MeasurementUnit

enum class Frequency(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.hz,
        "Hz",
        {it},
        {it}
    ),
    BASE2(
        R.string.khz,
        "kHz",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    MHZ(
        R.string.mhz,
        "MHz",
        { divide(it, MILLION) },
        { multiply(it, MILLION) }
    ),
    GHZ(
        R.string.ghz,
        "GHz",
        { divide(it, BILLION) },
        { multiply(it, BILLION) }
    )
}