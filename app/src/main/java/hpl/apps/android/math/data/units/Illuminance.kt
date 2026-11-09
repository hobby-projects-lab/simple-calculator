package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.TEN_THOUSAND
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.sqFt
import hpl.apps.android.math.utils.MeasurementUnit
import java.math.BigDecimal

enum class Illuminance(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (BigDecimal) -> BigDecimal,
    override val toBase: (BigDecimal) -> BigDecimal
): MeasurementUnit {
    BASE(
        R.string.lux,
        "lx",
        {it},
        {it}
    ),
    BASE2(
        R.string.foot_candle,
        "fc",
        { multiply(it, sqFt()) },
        { divide(it, sqFt()) }
    ),
    PHOT(
        R.string.phot,
        "ph",
        { divide(it, TEN_THOUSAND) },
        { multiply(it, TEN_THOUSAND) }
    ),
    NX(
        R.string.nx,
        "nx",
        { multiply(it, THOUSAND) },
        { divide(it, THOUSAND) }
    )
}