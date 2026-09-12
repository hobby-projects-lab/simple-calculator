package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.TEN_THOUSAND
import hpl.apps.android.math.data.units.utils.ac
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.sq_ft
import hpl.apps.android.math.data.units.utils.sq_in
import hpl.apps.android.math.data.units.utils.sq_mi
import hpl.apps.android.math.data.units.utils.sq_yd
import hpl.apps.android.math.utils.MeasurementUnit

enum class Area(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.m2,
        "m²",
        {it},
        {it}
    ),
    BASE2(
        R.string.km2,
        "km²",
        { divide(it, MILLION) },
        { multiply(it, MILLION) }
    ),
    CM2(
        R.string.cm2,
        "cm²",
        { multiply(it, TEN_THOUSAND) },
        { divide(it, TEN_THOUSAND) }
    ),
    MM2(
        R.string.mm2,
        "mm²",
        { multiply(it, MILLION) },
        { divide(it, MILLION) }
    ),
    MI2(
        R.string.mi2,
        "sq mi",
        { divide(it, sq_mi) },
        { multiply(it, sq_mi) }
    ),
    YD2(
        R.string.yd2,
        "sq yd",
        { divide(it, sq_yd) },
        { multiply(it, sq_yd) }
    ),
    FT2(
        R.string.ft2,
        "sq ft",
        { divide(it, sq_ft) },
        { multiply(it, sq_ft) }
    ),
    IN2(
        R.string.in2,
        "sq in",
        { divide(it, sq_in) },
        { multiply(it, sq_in) }
    ),
    HA(
        R.string.ha,
        "ha",
        { divide(it, TEN_THOUSAND) },
        { multiply(it, TEN_THOUSAND) }
    ),
    AC(
        R.string.ac,
        "ac",
        { divide(it, ac) },
        { multiply(it, ac) }
    )
}