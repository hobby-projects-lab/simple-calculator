package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.lb_ft3
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.oz_in3
import hpl.apps.android.math.utils.MeasurementUnit

enum class Density(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.g_l,
        "g/l",
        {it},
        {it}
    ),
    KG_L(
        R.string.kg_l,
        "kg/l",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    BASE2(
        R.string.g_cm3,
        "g/cm³",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    KG_CM3(
        R.string.kg_cm3,
        "kg/cm³",
        { divide(it, MILLION) },
        { multiply(it, MILLION) }
    ),
    KG_M3(
        R.string.kg_m3,
        "kg/m³",
        {it},
        {it}
    ),
    LB_FT3(
        R.string.lb_ft3,
        "lb/ft³",
        { divide(it, lb_ft3) },
        { multiply(it, lb_ft3) }
    ),
    OZ_IN3(
        R.string.oz_in3,
        "oz/in³",
        { divide(it, oz_in3) },
        { multiply(it, oz_in3) }
    )
}