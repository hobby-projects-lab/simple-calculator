package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.c
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.ftps
import hpl.apps.android.math.data.units.utils.kmph
import hpl.apps.android.math.data.units.utils.knot
import hpl.apps.android.math.data.units.utils.mph
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.utils.MeasurementUnit

enum class Speed(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.mps,
        "m/s",
        {it},
        {it}
    ),
    BASE2(
    R.string.kmph,
        "km/h",
    { divide(it, kmph) },
    { multiply(it, kmph) }
    ),
    KMPS(
        R.string.kmps,
        "km/s",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    MPH(
        R.string.mph,
        "mi/h",
        { divide(it, mph) },
        { multiply(it, mph) }
    ),
    FTPS(
    R.string.ftps,
        "ft/s",
    { divide(it, ftps) },
    { multiply(it, ftps) }
    ),
    KN(
        R.string.kn,
        "kn",
        { divide(it, knot) },
        { multiply(it, knot) }
    ),
    C(
        R.string.light_speed,
        "c",
        { divide(it, c) },
        { multiply(it, c) }
    )
}