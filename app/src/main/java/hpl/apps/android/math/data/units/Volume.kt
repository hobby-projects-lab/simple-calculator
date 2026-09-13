package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.BILLION
import hpl.apps.android.math.data.units.utils.HUNDRED_THOUSAND
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.ac_ft
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.ft3
import hpl.apps.android.math.data.units.utils.in3
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.utils.MeasurementUnit

enum class Volume(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.m3,
        "m³",
        {it},
        {it}
    ),
    CM3(
        R.string.cm3,
        "cm³",
        { multiply(it, MILLION) },
        { divide(it, MILLION) }
    ),
    MM3(
        R.string.mm3,
        "mm³",
        { multiply(it, BILLION) },
        { divide(it, BILLION) }
    ),
    BASE2(
        R.string.l,
        "L",
        { multiply(it, THOUSAND) },
        { divide(it, THOUSAND) }
    ),
    CL(
        R.string.cl,
        "cL",
        { multiply(it, HUNDRED_THOUSAND) },
        { divide(it, HUNDRED_THOUSAND) }
    ),
    ML(
        R.string.ml,
        "mL",
        { multiply(it, MILLION) },
        { divide(it, MILLION) }
    ),
    AC_FT(
        R.string.ac_ft,
        "ac ft",
        { divide(it, ac_ft) },
        { multiply(it, ac_ft) }
    ),
    FT3(
        R.string.ft3,
        "ft³",
        { divide(it, ft3) },
        { multiply(it, ft3) }
    ),
    IN3(
        R.string.in3,
        "in³",
        { divide(it, in3) },
        { multiply(it, in3) }
    )
}