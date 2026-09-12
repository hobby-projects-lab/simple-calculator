package hpl.apps.android.math.data.units.utils

import hpl.apps.android.math.data.KevalType


/* Time */

//day in second
val day = multiply(D24, h)

//week in second
val week = multiply(D7, day)

//year in seconds
val year = multiply(D365, day)
//leap year in seconds
val leap_year = multiply(D366, day)



/* Angle */
//radian in degree
val rad = divide(D180, pi())



/* Energy */
//kilocalorie in joule
val kcal = multiply(cal, THOUSAND)



/* Mass */
//pound in gram
val lb = multiply(D0_45359237, THOUSAND)

//ounce in gram
val oz = divide(lb, D16)



/* Speed */
//km/h in m/s
val kmph = divide(THOUSAND, h)

//knot in m/s
val knot = divide(multiply(D1_852, THOUSAND), D3600)

//mile per hour in m/s
val mph = divide(mi, D3600)



/* Area */
// square yard in m²
val sq_yd = multiply(yd, yd)

//square mile in m²
val sq_mi = multiply(mi, mi)

//square foot in m²
val sq_ft = multiply(ft, ft)

//square inch in m²
val sq_in = multiply(inch, inch)

//acre in m²
val ac = multiply(D4840, sq_yd)



/* Volume */
//cubic foot in cubic metre
val ft3 = multiply(ft, sq_ft)

//cubic inch in cubic metre
val in3 = multiply(inch, sq_in)

//acre foot in cubic metre
val ac_ft = multiply(ac, ft)



/* Density */
//pound per cubic foot in g/l
val lb_ft3 = divide(lb, multiply(ft3, THOUSAND))

//pound per cubic foot in g/l
val oz_in3 = divide(oz, multiply(in3, THOUSAND))



/* Force */
//pound-force in newton
val lbf = multiply(divide(lb, THOUSAND), g)
val ozf = divide(lbf, D16)



/* Work */
//pound foot in N m
val lbft = multiply(lbf, ft)

//ounce inch in N m
val ozin = multiply(ozf, inch)



/* Power */
//horsepower in watt
val hp = divide(multiply(D33E3, lbft), min)

//BTU/h in watt
val btuph = divide(btu, h)



/* Pressure */

//torr in pascal
val torr = divide(atm, D760)

//pound per square inch in pascal
val psi = divide(lbf, sq_in)



/* Storage in bit*/
val mib = multiply(D1024, kib)
val gib = multiply(D1024, mib)
val tib = multiply(D1024, gib)

val kB = multiply(THOUSAND, b)
val kiB = multiply(D1024, b)

val mB = multiply(THOUSAND, kB)
val miB = multiply(D1024, kiB)

val gB = multiply(THOUSAND, mB)
val giB = multiply(D1024, miB)

val tB = multiply(THOUSAND, gB)
val tiB = multiply(D1024, giB)



fun celsiusToFahrenheit(celsius: Double): Double{
    return add(multiply(celsius, D1_8), D32)
}

fun fahrenheitToCelsius(fahrenheit: Double): Double{
    return divide(subtract(fahrenheit, D32), D1_8)
}

fun celsiusToRankine(celsius: Double): Double{
    return multiply(subtract(celsius, abs_zero), D1_8)

}

fun rankineToCelsius(rankine: Double): Double{
    return add(divide(rankine, D1_8), abs_zero)
}





fun divide(x: Double, y: Double): Double{
    return KevalType.divide(x, y)
}

fun multiply(x: Double, y: Double): Double{
    return KevalType.multiply(x, y)
}

fun add(x: Double, y: Double): Double{
    return KevalType.add(x, y)
}

fun subtract(x: Double, y: Double): Double{
    return KevalType.subtract(x, y)
}

fun pi(): Double = KevalType.pi()