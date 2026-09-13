package hpl.apps.android.math.data.units

import androidx.annotation.StringRes
import hpl.apps.android.math.R
import hpl.apps.android.math.data.units.utils.BILLION
import hpl.apps.android.math.data.units.utils.MILLION
import hpl.apps.android.math.data.units.utils.THOUSAND
import hpl.apps.android.math.data.units.utils.day
import hpl.apps.android.math.data.units.utils.divide
import hpl.apps.android.math.data.units.utils.h
import hpl.apps.android.math.data.units.utils.leap_year
import hpl.apps.android.math.data.units.utils.min
import hpl.apps.android.math.data.units.utils.multiply
import hpl.apps.android.math.data.units.utils.week
import hpl.apps.android.math.data.units.utils.year
import hpl.apps.android.math.utils.MeasurementUnit

enum class Time(
    @StringRes override val id: Int,
    override val symbol: String,
    override val fromBase: (Double) -> Double,
    override val toBase: (Double) -> Double
): MeasurementUnit {
    BASE(
        R.string.sec,
        "s",
        {it},
        {it}
    ),
    MIN(
    R.string.min,
        "min",
    { divide(it, min) },
    { multiply(it, min) }
    ),
    BASE2(
    R.string.h,
        "h",
    { divide(it, h) },
    { multiply(it, h) }
    ),
    MS(
        R.string.ms,
        "ms",
        { multiply(it, THOUSAND) },
        { divide(it, THOUSAND) }
    ),
    US(
        R.string.us,
        "μs",
        { multiply(it, MILLION) },
        { divide(it, MILLION) }
    ),
    NS(
        R.string.ns,
        "ns",
        { multiply(it, BILLION) },
        { divide(it, BILLION) }
    ),
    D(
    R.string.d,
        "d",
    { divide(it, day) },
    { multiply(it, day) }
    ),
    WK(
        R.string.wk,
        "wk",
        { divide(it, week) },
        { multiply(it, week) }
    ),
    Y(
        R.string.yr,
        "yr",
        { divide(it, year) },
        { multiply(it, year) }
    ),
    LEAP_Y(
        R.string.leap_yr,
        "leap yr",
        { divide(it, leap_year) },
        { multiply(it, leap_year) }
    )
}