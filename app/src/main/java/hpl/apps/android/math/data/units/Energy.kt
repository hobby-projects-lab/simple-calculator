package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.BILLION
import hpl.apps.android.math.data.units.utils.D3600
import hpl.apps.android.math.data.units.utils.D3600E3
import hpl.apps.android.math.data.units.utils.D3600E6
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.TEN_MILLION
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.btu
import hpl.apps.android.math.data.units.utils.cal
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.ev
import hpl.apps.android.math.data.units.utils.kcal
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.utils.MeasurementUnit

enum class Energy(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.j,
        "J",
        {it},
        {it}
    ),
    KJ(
        R.string.kj,
        "kJ",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    MJ(
        R.string.mj,
        "MJ",
        { divide(it, MILLION) },
        { multiply(it, MILLION) }
    ),
    GJ(
        R.string.gj,
        "GJ",
        { divide(it, BILLION) },
        { multiply(it, BILLION) }
    ),
    BASE2(
        R.string.cal,
        "cal",
        { divide(it, cal) },
        { multiply(it, cal) }
    ),
    KCAL(
        R.string.kcal,
        "kcal",
        { divide(it, kcal) },
        { multiply(it, kcal) }
    ),
    WH(
        R.string.wh,
        "Wh",
        { divide(it, D3600) },
        { multiply(it, D3600) }
    ),
    KWH(
        R.string.kwh,
        "kWh",
        { divide(it, D3600E3) },
        { multiply(it, D3600E3) }
    ),
    MWH(
        R.string.mwh,
        "MWh",
        { divide(it, D3600E6) },
        { multiply(it, D3600E6) }
    ),
    EV(
        R.string.ev,
        "eV",
        { divide(it, ev) },
        { multiply(it, ev) }
    ),
    ERG(
        R.string.erg,
        "erg",
        { multiply(it, TEN_MILLION) },
        { divide(it, TEN_MILLION) }
    ),
    BTU(
        R.string.btu,
        "BTU",
        { divide(it, btu) },
        { multiply(it, btu) }
    )
}