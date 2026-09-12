package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.HUNDRED_THOUSAND
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.lbf
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.ozf
import hpl.apps.android.math.utils.MeasurementUnit

enum class Force(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.n,
        "N",
        {it},
        {it}
    ),
    KN(
        R.string.kN,
        "kN",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    DYN(
        R.string.dyn,
        "dyn",
        { multiply(it, HUNDRED_THOUSAND) },
        { divide(it, HUNDRED_THOUSAND) }
    ),
    BASE2(
        R.string.lbf,
        "lbf",
        { divide(it, lbf) },
        { multiply(it, lbf) }
    ),
    OZF(
        R.string.ozf,
        "ozf",
        { divide(it, ozf) },
        { multiply(it, ozf) }
    )
}