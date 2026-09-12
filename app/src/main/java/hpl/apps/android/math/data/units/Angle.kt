package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.gon
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.rad
import hpl.apps.android.math.data.units.utils.tr
import hpl.apps.android.math.utils.MeasurementUnit

enum class Angle(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.deg,
        "deg",
        {it},
        {it}
    ),
    BASE2(
        R.string.rad,
        "rad",
        { divide(it, rad) },
        { multiply(it, rad) }
    ),
    GON(
        R.string.gon,
        "gon",
        { divide(it, gon) },
        { multiply(it, gon) }
    ),
    TR(
        R.string.tr,
        "tr",
        { divide(it, tr) },
        { multiply(it, tr) }
    )
}