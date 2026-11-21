package hpl.apps.android.math.data.units.utils

import hpl.apps.android.math.data.CalculatorException
import hpl.apps.android.math.data.KevalType
import java.math.BigDecimal


/* Length in metre*/
val au: BigDecimal   = BigDecimal.valueOf(149_597_870_700L, 0)
val ft: BigDecimal   = BigDecimal.valueOf(3_048L, 4)
val inch: BigDecimal = BigDecimal.valueOf(254L, 4)
val ly: BigDecimal   = BigDecimal.valueOf(9_460_730_472_580_800L, 0)
val mi: BigDecimal   = BigDecimal.valueOf(1_609_344L, 3)
val yd: BigDecimal   = BigDecimal.valueOf(9_144L, 4)



/* Time in second */
val min: BigDecimal = BigDecimal.valueOf(60L, 0)
val h: BigDecimal   = BigDecimal.valueOf(3_600L, 0)
fun day(): BigDecimal = multiply(D24, h)
fun week(): BigDecimal = multiply(D7, day())
fun year(): BigDecimal = multiply(D365, day())
fun leapYear(): BigDecimal = multiply(D366, day())



/* Angle in degree */
fun rad(): BigDecimal = divide(D180, KevalType.pi())
val gon: BigDecimal = BigDecimal.valueOf(9L, 1)
val tr: BigDecimal  = BigDecimal.valueOf(360L, 0)


/* Energy in joule */
fun kcal(): BigDecimal = multiply(cal, THOUSAND)
val ev: BigDecimal  = BigDecimal.valueOf(1_602_176_634L, 28)
val cal: BigDecimal = BigDecimal.valueOf(4_184L, 3)
val btu: BigDecimal = BigDecimal.valueOf(105_505_585_262L, 8)


/* Mass in gram */
fun lb(): BigDecimal = multiply(D0_45359237, THOUSAND)
fun oz(): BigDecimal = divide(lb(), D16)



/* Speed in metre/second */
val ftps: BigDecimal = ft
val c: BigDecimal    = BigDecimal.valueOf(299_792_458L, 0)
fun kmph(): BigDecimal = divide(THOUSAND, h)
fun knot(): BigDecimal = divide(multiply(D1_852, THOUSAND), D3600)
fun mph(): BigDecimal = divide(mi, D3600)



/* Acceleration in metre/second² */
val g: BigDecimal = BigDecimal.valueOf(980_665L, 5)


/* Area in metre² */
fun sqYd(): BigDecimal = multiply(yd, yd)
fun sqMi(): BigDecimal = multiply(mi, mi)
fun sqFt(): BigDecimal = multiply(ft, ft)
fun sqIn(): BigDecimal = multiply(inch, inch)
fun ac(): BigDecimal = multiply(D4840, sqYd())



/* Volume in cubic metre */
fun ft3(): BigDecimal = multiply(ft, sqFt())
fun in3(): BigDecimal = multiply(inch, sqIn())
fun acFt(): BigDecimal = multiply(ac(), ft)



/* Density in gram/litre */
fun lbFt3(): BigDecimal = divide(lb(), multiply(ft3(), THOUSAND))
fun ozIn3(): BigDecimal = divide(oz(), multiply(in3(), THOUSAND))



/* Force in newton */
fun lbf(): BigDecimal = multiply(divide(lb(), THOUSAND), g)
fun ozf(): BigDecimal = divide(lbf(), D16)



/* Work in newton metre */
fun lbft(): BigDecimal = multiply(lbf(), ft)
fun ozin(): BigDecimal = multiply(ozf(), inch)



/* Power in watt */
fun hp(): BigDecimal = divide(multiply(D33E3, lbft()), min)
fun btuph(): BigDecimal = divide(btu, h)



/* Pressure in pascal */
val atm: BigDecimal  = BigDecimal.valueOf(101_325L, 0)
val mmHg: BigDecimal = BigDecimal.valueOf(133_322_387_415L, 9)
val inHg: BigDecimal = BigDecimal.valueOf(3_386_389L, 3)
fun torr(): BigDecimal = divide(atm, D760)
fun psi(): BigDecimal = divide(lbf(), sqIn())



/* Storage in bit */
val kib: BigDecimal = D1024
val b: BigDecimal   = BigDecimal.valueOf(8L, 0)
fun mib(): BigDecimal = multiply(D1024, kib)
fun gib(): BigDecimal = multiply(D1024, mib())
fun tib(): BigDecimal = multiply(D1024, gib())

fun kB(): BigDecimal = multiply(THOUSAND, b)
fun kiB(): BigDecimal = multiply(D1024, b)

fun mB(): BigDecimal = multiply(THOUSAND, kB())
fun miB(): BigDecimal = multiply(D1024, kiB())

fun gB(): BigDecimal = multiply(THOUSAND, mB())
fun giB(): BigDecimal = multiply(D1024, miB())

fun tB(): BigDecimal = multiply(THOUSAND, gB())
fun tiB(): BigDecimal = multiply(D1024, giB())


/* Temperature in Celsius */
val abs_zero: BigDecimal = BigDecimal.valueOf(-27_315L, 2)

fun celsiusToFahrenheit(celsius: BigDecimal): BigDecimal{
    return fromCelsius(celsius){ add(multiply(it, D1_8), D32) }
}
fun fahrenheitToCelsius(fahrenheit: BigDecimal): BigDecimal{
    return toCelsius(fahrenheit){ divide(subtract(it, D32), D1_8) }
}

fun celsiusToRankine(celsius: BigDecimal): BigDecimal{
    return fromCelsius(celsius){ multiply(subtract(it, abs_zero), D1_8) }

}
fun rankineToCelsius(rankine: BigDecimal): BigDecimal{
    return toCelsius(rankine){ add(divide(it, D1_8), abs_zero) }
}

fun celsiusToCelsius(celsius: BigDecimal): BigDecimal{
    return fromCelsius(celsius){it}
}

private fun fromCelsius(celsius: BigDecimal, func: (BigDecimal)-> BigDecimal): BigDecimal{
    return KevalType.compute(listOf(celsius)){
        if(it[0] < abs_zero) CalculatorException.tempBelowAbsZero()
        func(it[0])
    }
}
private fun toCelsius(from: BigDecimal, func: (BigDecimal)-> BigDecimal): BigDecimal{
    return KevalType.compute(listOf(from)){
        val x = func(it[0])
        if(x < abs_zero) CalculatorException.tempBelowAbsZero()
        x
    }
}





fun divide(x: BigDecimal, y: BigDecimal): BigDecimal{
    return KevalType.divide(x, y)
}

fun multiply(x: BigDecimal, y: BigDecimal): BigDecimal{
    return KevalType.multiply(x, y)
}

fun add(x: BigDecimal, y: BigDecimal): BigDecimal{
    return KevalType.add(x, y)
}

fun subtract(x: BigDecimal, y: BigDecimal): BigDecimal{
    return KevalType.subtract(x, y)
}
