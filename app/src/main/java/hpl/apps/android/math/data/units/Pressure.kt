package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.HUNDRED
import hpl.apps.android.math.data.units.utils.HUNDRED_THOUSAND
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.atm
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.inHg
import hpl.apps.android.math.data.units.utils.mmHg
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.psi
import hpl.apps.android.math.data.units.utils.torr
import hpl.apps.android.math.utils.MeasurementUnit

enum class Pressure(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.pa,
        "Pa",
        {it},
        {it}
    ),
    KPA(
        R.string.kpa,
        "kPa",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    MPA(
    R.string.mpa,
        "MPa",
    { divide(it, MILLION) },
    { multiply(it, MILLION) }
    ),
    BASE2(
        R.string.bar,
        "bar",
        { divide(it, HUNDRED_THOUSAND) },
        { multiply(it, HUNDRED_THOUSAND) }
    ),
    MBAR(
        R.string.mbar,
        "mbar",
        { divide(it, HUNDRED) },
        { multiply(it, HUNDRED) }
    ),
    ATM(
        R.string.atm,
        "atm",
        { divide(it, atm) },
        { multiply(it, atm) }
    ),
    TORR(
        R.string.torr,
        "Torr",
        { divide(it, torr) },
        { multiply(it, torr) }
    ),
    PSI(
        R.string.psi,
        "psi",
        { divide(it, psi) },
        { multiply(it, psi) }
    ),
    MMHG(
        R.string.mmhg,
        "mmHg",
        { divide(it, mmHg) },
        { multiply(it, mmHg) }
    ),
    INHG(
        R.string.inhg,
        "inHg",
        { divide(it, inHg) },
        { multiply(it, inHg) }
    )
}