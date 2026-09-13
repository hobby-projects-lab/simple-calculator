package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.BILLION
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.btuph
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.hp
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.utils.MeasurementUnit

enum class Power(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.w,
        "W",
        {it},
        {it}
    ),
    BASE2(
        R.string.kw,
        "kW",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    MW(
        R.string.mw,
        "MW",
        { divide(it, MILLION) },
        { multiply(it, MILLION) }
    ),
    GW(
        R.string.gw,
        "GW",
        { divide(it, BILLION) },
        { multiply(it, BILLION) }
    ),
    HP(
        R.string.hp,
        "HP",
        { divide(it, hp) },
        { multiply(it, hp) }
    ),
    BTUPH(
        R.string.btuph,
        "BTU/h",
        { divide(it, btuph) },
        { multiply(it, btuph) }
    )
}