package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.D3600
import hpl.apps.android.math.data.units.utils.HUNDRED
import hpl.apps.android.math.data.units.utils.HUNDRED_MILLION
import hpl.apps.android.math.data.units.utils.HUNDRED_THOUSAND
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.ft
import hpl.apps.android.math.data.units.utils.g
import hpl.apps.android.math.data.units.utils.knot
import hpl.apps.android.math.data.units.utils.mi
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.utils.MeasurementUnit
import java.math.BigDecimal

enum class Acceleration(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (BigDecimal) -> BigDecimal,
    override val toBase: (BigDecimal) -> BigDecimal
): MeasurementUnit {
    BASE(
        R.string.mps_squared,
        "m/s²",
        {it},
        {it}
    ),
    BASE2(
        R.string.g_force,
        "g",
        { divide(it, g) },
        { multiply(it, g) }
    ),
    GAL(
        R.string.gal,
        "Gal",
        { multiply(it, HUNDRED) },
        { divide(it, HUNDRED) }
    ),
    MGAL(
        R.string.mgal,
        "mGal",
        { multiply(it, HUNDRED_THOUSAND) },
        { divide(it, HUNDRED_THOUSAND) }
    ),
    UGAL(
        R.string.ugal,
        "µGal",
        { multiply(it, HUNDRED_MILLION) },
        { divide(it, HUNDRED_MILLION) }
    ),
    KMPHPS(
        R.string.kmphps,
        "km/h/s",
        { multiply(divide(it, THOUSAND), D3600) },
        { divide(multiply(it, THOUSAND), D3600) }
    ),
    KMPS2(
        R.string.kmps_squared,
        "km/s²",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    MPHPS(
        R.string.mphps,
        "mi/h/s",
        { multiply(divide(it, mi), D3600) },
        { divide(multiply(it, mi), D3600) }
    ),
    FPS2(
        R.string.ftps_squared,
        "ft/s²",
        { divide(it, ft) },
        { multiply(it, ft) }
    ),
    KNPS(
        R.string.knps,
        "kn/s",
        { divide(it, knot()) },
        { multiply(it, knot()) }
    )
}