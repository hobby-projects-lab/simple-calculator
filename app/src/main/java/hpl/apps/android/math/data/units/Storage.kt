package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.BILLION
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.TRILLION
import hpl.apps.android.math.data.units.utils.b
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.gB
import hpl.apps.android.math.data.units.utils.giB
import hpl.apps.android.math.data.units.utils.gib
import hpl.apps.android.math.data.units.utils.kB
import hpl.apps.android.math.data.units.utils.kiB
import hpl.apps.android.math.data.units.utils.kib
import hpl.apps.android.math.data.units.utils.mB
import hpl.apps.android.math.data.units.utils.miB
import hpl.apps.android.math.data.units.utils.mib
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.tB
import hpl.apps.android.math.data.units.utils.tiB
import hpl.apps.android.math.data.units.utils.tib
import hpl.apps.android.math.utils.MeasurementUnit

enum class Storage(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.bit,
        "bit",
        {it},
        {it}
    ),
    BASE2(
        R.string.B,
        "B",
        { divide(it, b) },
        { multiply(it, b) }
    ),
    KBIT(
        R.string.kbit,
        "kbit",
        { divide(it, THOUSAND) },
        { multiply(it, THOUSAND) }
    ),
    KIBIT(
        R.string.Kibit,
        "Kibit",
        { divide(it, kib) },
        { multiply(it, kib) }
    ),
    KB(
        R.string.kB,
        "kB",
        { divide(it, kB) },
        { multiply(it, kB) }
    ),
    KIB(
        R.string.KiB,
        "KiB",
        { divide(it, kiB) },
        { multiply(it, kiB) }
    ),
    MBIT(
        R.string.Mbit,
        "Mbit",
        { divide(it, MILLION) },
        { multiply(it, MILLION) }
    ),
    MIBIT(
        R.string.Mibit,
        "Mibit",
        { divide(it, mib) },
        { multiply(it, mib) }
    ),
    MB(
        R.string.MB,
        "MB",
        { divide(it, mB) },
        { multiply(it, mB) }
    ),
    MIB(
        R.string.MiB,
        "MiB",
        { divide(it, miB) },
        { multiply(it, miB) }
    ),
    GBIT(
        R.string.Gbit,
        "Gbit",
        { divide(it, BILLION) },
        { multiply(it, BILLION) }
    ),
    GIBIT(
        R.string.Gibit,
        "Gibit",
        { divide(it, gib) },
        { multiply(it, gib) }
    ),
    GB(
        R.string.GB,
        "GB",
        { divide(it, gB) },
        { multiply(it, gB) }
    ),
    GIB(
        R.string.GiB,
        "GiB",
        { divide(it, giB) },
        { multiply(it, giB) }
    ),
    TBIT(
        R.string.Tbit,
        "Tbit",
        { divide(it, TRILLION) },
        { multiply(it, TRILLION) }
    ),
    TIBIT(
        R.string.Tibit,
        "Tibit",
        { divide(it, tib) },
        { multiply(it, tib) }
    ),
    TB(
        R.string.TB,
        "TB",
        { divide(it, tB) },
        { multiply(it, tB) }
    ),
    TIB(
        R.string.TiB,
        "TiB",
        { divide(it, tiB) },
        { multiply(it, tiB) }
    )
}