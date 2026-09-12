package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.BILLION
import hpl.apps.android.math.data.units.utils.HUNDRED
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.TEN_BILLION
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.au
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.ft
import hpl.apps.android.math.data.units.utils.inch
import hpl.apps.android.math.data.units.utils.ly
import hpl.apps.android.math.data.units.utils.mi
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.yd
import hpl.apps.android.math.utils.MeasurementUnit

enum class Length(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.m,
        "m",
        {it},
        {it}
    ),
    KM(
        R.string.km,
        "km",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    CM(
        R.string.cm,
        "cm",
        { multiply(it, HUNDRED) },
        { divide(it, HUNDRED) }
    ),
    MM(
        R.string.mm,
        "mm",
    { multiply(it, THOUSAND) },
    { divide(it, THOUSAND) }
    ),
    UM(
        R.string.um,
        "μm",
        { multiply(it, MILLION) },
        { divide(it, MILLION) }
    ),
    NM(
        R.string.nm,
        "nm",
        { multiply(it, BILLION) },
        { divide(it, BILLION) }
    ),
    A(
        R.string.a,
        "Å",
        { multiply(it, TEN_BILLION) },
        { divide(it, TEN_BILLION) }
    ),
    MI(
        R.string.mi,
        "mi",
        { divide(it, mi) },
        { multiply(it, mi) }
    ),
    YD(
        R.string.yd,
        "yd",
        { divide(it, yd) },
        { multiply(it, yd) }
    ),
    BASE2(
        R.string.ft,
        "ft",
        { divide(it, ft) },
        { multiply(it, ft) }
    ),
    IN(
        R.string.inch,
        "in",
        { divide(it, inch) },
        { multiply(it, inch) }
    ),
    AU(
        R.string.au,
        "au",
        { divide(it, au) },
        { multiply(it, au) }
    ),
    LY(
        R.string.ly,
        "ly",
        { divide(it, ly) },
        { multiply(it, ly) }
    )
}