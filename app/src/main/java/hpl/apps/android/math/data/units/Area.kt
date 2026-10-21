package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.TEN_THOUSAND
import hpl.apps.android.math.data.units.utils.ac
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.sqFt
import hpl.apps.android.math.data.units.utils.sqIn
import hpl.apps.android.math.data.units.utils.sqMi
import hpl.apps.android.math.data.units.utils.sqYd
import hpl.apps.android.math.utils.MeasurementUnit
import java.math.BigDecimal

enum class Area(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (BigDecimal) -> BigDecimal,
    override val toBase: (BigDecimal) -> BigDecimal
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
        { divide(it, sqMi()) },
        { multiply(it, sqMi()) }
    ),
    YD2(
        R.string.yd2,
        "sq yd",
        { divide(it, sqYd()) },
        { multiply(it, sqYd()) }
    ),
    FT2(
        R.string.ft2,
        "sq ft",
        { divide(it, sqFt()) },
        { multiply(it, sqFt()) }
    ),
    IN2(
        R.string.in2,
        "sq in",
        { divide(it, sqIn()) },
        { multiply(it, sqIn()) }
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
        { divide(it, ac()) },
        { multiply(it, ac()) }
    )
}