package hpl.apps.android.math.data.units.utils

import hpl.apps.android.math.data.CalculatorException
import hpl.apps.android.math.data.KevalType
import java.math.BigDecimal


/* Time */

//day in seconds
fun day(): BigDecimal = multiply(D24, h)

//week in seconds
fun week(): BigDecimal = multiply(D7, day())

//year in seconds
fun year(): BigDecimal = multiply(D365, day())
//leap year in seconds
fun leapYear(): BigDecimal = multiply(D366, day())



/* Angle */
//radian in degree
fun rad(): BigDecimal = divide(D180, KevalType.pi())



/* Energy */
//kilocalorie in joule
fun kcal(): BigDecimal = multiply(cal, THOUSAND)



/* Mass */
//pound in gram
fun lb(): BigDecimal = multiply(D0_45359237, THOUSAND)

//ounce in gram
fun oz(): BigDecimal = divide(lb(), D16)



/* Speed */
//km/h in m/s
fun kmph(): BigDecimal = divide(THOUSAND, h)

//knot in m/s
fun knot(): BigDecimal = divide(multiply(D1_852, THOUSAND), D3600)

//mile per hour in m/s
fun mph(): BigDecimal = divide(mi, D3600)



/* Area */
// square yard in m²
fun sqYd(): BigDecimal = multiply(yd, yd)

//square mile in m²
fun sqMi(): BigDecimal = multiply(mi, mi)

//square foot in m²
fun sqFt(): BigDecimal = multiply(ft, ft)

//square inch in m²
fun sqIn(): BigDecimal = multiply(inch, inch)

//acre in m²
fun ac(): BigDecimal = multiply(D4840, sqYd())



/* Volume */
//cubic foot in cubic metre
fun ft3(): BigDecimal = multiply(ft, sqFt())

//cubic inch in cubic metre
fun in3(): BigDecimal = multiply(inch, sqIn())

//acre foot in cubic metre
fun acFt(): BigDecimal = multiply(ac(), ft)



/* Density */
//pound per cubic foot in g/l
fun lbFt3(): BigDecimal = divide(lb(), multiply(ft3(), THOUSAND))

//pound per cubic foot in g/l
fun ozIn3(): BigDecimal = divide(oz(), multiply(in3(), THOUSAND))



/* Force */
//pound-force in newton
fun lbf(): BigDecimal = multiply(divide(lb(), THOUSAND), g)
fun ozf(): BigDecimal = divide(lbf(), D16)



/* Work */
//pound foot in N m
fun lbft(): BigDecimal = multiply(lbf(), ft)

//ounce inch in N m
fun ozin(): BigDecimal = multiply(ozf(), inch)



/* Power */
//horsepower in watt
fun hp(): BigDecimal = divide(multiply(D33E3, lbft()), min)

//BTU/h in watt
fun btuph(): BigDecimal = divide(btu, h)



/* Pressure */

//torr in pascal
fun torr(): BigDecimal = divide(atm, D760)

//pound per square inch in pascal
fun psi(): BigDecimal = divide(lbf(), sqIn())



/* Storage in bit*/
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
