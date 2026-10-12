package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.lbft
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.ozin
import hpl.apps.android.math.utils.MeasurementUnit
import java.math.BigDecimal

enum class Work(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (BigDecimal) -> BigDecimal,
    override val toBase: (BigDecimal) -> BigDecimal
): MeasurementUnit {
    BASE(
        R.string.n_m,
        "N·m",
        {it},
        {it}
    ),
    KNM(
        R.string.kn_m,
        "kN·m",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    BASE2(
        R.string.lbft,
        "lbf·ft",
        { divide(it, lbft) },
        { multiply(it, lbft) }
    ),
    OZIN(
        R.string.ozin,
        "oz·in",
        { divide(it, ozin) },
        { multiply(it, ozin) }
    )
}