package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.lb
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.oz
import hpl.apps.android.math.utils.MeasurementUnit

enum class Mass(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.g,
        "g",
        {it},
        {it}
    ),
    BASE2(
    R.string.kg,
        "kg",
    { divide(it, THOUSAND) },
    { multiply(it, THOUSAND) }
    ),
    MG(
        R.string.mg,
        "mg",
        { multiply(it, THOUSAND) },
        { divide(it, THOUSAND) }
    ),
    UG(
        R.string.ug,
        "μg",
        { multiply(it, MILLION) },
        { divide(it, MILLION) }
    ),
    T(
        R.string.t,
        "t",
        { divide(it, MILLION) },
        { multiply(it, MILLION) }
    ),
    LB(
        R.string.lb,
        "lb",
        { divide(it, lb) },
        { multiply(it, lb) }
    ),
    OZ(
        R.string.oz,
        "oz",
        { divide(it, oz) },
        { multiply(it, oz) }
    )
}