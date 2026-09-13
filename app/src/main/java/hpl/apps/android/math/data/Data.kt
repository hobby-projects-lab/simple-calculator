package hpl.apps.android.math.data

import com.notkamui.keval.Keval
import com.notkamui.keval.KevalNumber
import com.notkamui.keval.KevalNumbers
import com.notkamui.keval.KevalOperator
import hpl.apps.android.math.utils.oops
import java.lang.StrictMath.toDegrees
import java.lang.StrictMath.toRadians
import kotlin.math.E
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.acosh
import kotlin.math.asin
import kotlin.math.asinh
import kotlin.math.atan
import kotlin.math.atanh
import kotlin.math.cos
import kotlin.math.cosh
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sinh
import kotlin.math.sqrt
import kotlin.math.tan
import kotlin.math.tanh

object Operator{
    const val ADD = '+'
    const val SUB = '-'
    const val MUL = '*'
    const val DIV = '/'
    const val POW = '^'
    const val MOD = '%'
    const val FACT = '!'
    const val PERCENT = '#'
    const val SQRT = '@'
}

object Expression{
    const val SCI_NOTATION = 'E'
    const val SCI_NOTATION_VALUE = "${Operator.MUL}10${Operator.POW}"
}

object MathFunction{// USE ONLY LETTERS FOR FUNCTIONS NAMES
    const val LOG = "log"
    const val LOG_X = "logX"
    const val LN = "ln"

    const val COS = "cos"
    const val SIN = "sin"
    const val TAN = "tan"
    const val ARCCOS = "arccos"
    const val ARCSIN = "arcsin"
    const val ARCTAN = "arctan"

    const val COSH = "cosh"
    const val SINH = "sinh"
    const val TANH = "tanh"

    const val ARCCOSH = "arccosh"
    const val ARCSINH = "arcsinh"
    const val ARCTANH = "arctanh"

    const val ABS = "abs"
    const val ROOT = "root"
}


object Constant{// USE ONLY LETTERS FOR CONSTANTS NAMES
    const val PI = "PI"
    const val E = "e"
}







object KevalType: KevalNumber<Double> {

    const val NEG_ONE = -1.0
    const val ONE = 1.0
    const val TEN = 10.0
    const val HUNDRED = 100.0

    override fun isValidLiteral(token: String): Boolean = KevalNumbers.real.isValidLiteral(token)

    override fun parseLiteral(token: String): Double = KevalNumbers.real.parseLiteral(token)

    override fun defaultResources(): Map<String, KevalOperator<Double>> = mapOf()

    fun pi(): Double = PI

    fun e(): Double = E

    override fun multiply(a: Double, b: Double): Double = a * b

    fun add(a: Double, b: Double): Double = a + b

    fun subtract(a: Double, b: Double): Double = a - b

    fun divide(a: Double, b: Double): Double = a / b

    fun factorial(a: Double): Double{
        if (a < 0.0) oops("Factorial of a negative number.")
        if (floor(a) != a) oops("Factorial of a non-integer.")
        var result: Double
        if(a > 170.0){
            result = Double.POSITIVE_INFINITY
        }else{
            result = 1.0
            var i = 2.0
            while (i<=a){
                result *= i
                if(result.isInfinite()){
                    break
                }
                i++
            }
        }
        return result
    }

    fun modulus(a: Double, b: Double): Double{
        if (b == 0.0) oops("Division by 0.")
        val result = a%b
        return if(result<0.0) result+b else result
    }

    fun power(a: Double, b: Double): Double = a.pow(b)

}
val evaluator = Keval.create(KevalType){

    unaryOperator {
        symbol = Operator.ADD
        isPrefix = true
        implementation = {a-> a}
    }

    binaryOperator {
        symbol = Operator.ADD
        precedence = 2
        isLeftAssociative = true
        implementation = {a, b -> KevalType.add(a, b) }
    }

    unaryOperator {
        symbol = Operator.SUB
        isPrefix = true
        implementation = {a-> KevalType.multiply(a, KevalType.NEG_ONE)}
    }

    binaryOperator {
        symbol = Operator.SUB
        precedence = 2
        isLeftAssociative = true
        implementation = {a, b -> KevalType.subtract(a, b) }
    }

    binaryOperator {
        symbol = Operator.DIV
        precedence = 3
        isLeftAssociative = true
        implementation = {a, b -> KevalType.divide(a, b) }
    }

    unaryOperator {
        symbol = Operator.FACT
        isPrefix = false
        implementation = {a-> KevalType.factorial(a) }
    }

    binaryOperator {
        symbol = Operator.MOD
        precedence = 3
        isLeftAssociative = true
        implementation = {a, b -> KevalType.modulus(a, b) }
    }

    binaryOperator {
        symbol = Operator.PERCENT
        precedence = 3
        isLeftAssociative = true
        implementation = { a, b -> KevalType.divide(KevalType.multiply(a, b), KevalType.HUNDRED) }
    }

    unaryOperator {
        symbol = Operator.SQRT
        isPrefix = true
        implementation = { a -> sqrt(a) }
    }

    binaryOperator {
        symbol = Operator.SQRT
        precedence = 3
        isLeftAssociative = false
        implementation = { a, b -> KevalType.multiply(a, sqrt(b)) }
    }

    binaryOperator {
        symbol = Operator.POW
        precedence = 4
        isLeftAssociative = false
        implementation = {a, b -> KevalType.power(a, b) }
    }

    function {
        name = MathFunction.LN
        arity = 1
        implementation = { args -> ln(args[0]) }
    }

    function {
        name = MathFunction.LOG
        arity = 1
        implementation = { args -> log(args[0], KevalType.TEN) }
    }

    function {
        name = MathFunction.LOG_X
        arity = 2
        implementation = { args-> log(args[1], args[0]) }
    }

    function {
        name = MathFunction.COS
        arity = 1
        implementation = { args -> cos(args[0]) }
    }

    function {
        name = MathFunction.SIN
        arity = 1
        implementation = { args -> sin(args[0]) }
    }

    function {
        name = MathFunction.TAN
        arity = 1
        implementation = { args -> tan(args[0]) }
    }

    function {
        name = MathFunction.ARCCOS
        arity = 1
        implementation = { args -> acos(args[0]) }
    }

    function {
        name = MathFunction.ARCSIN
        arity = 1
        implementation = { args -> asin(args[0]) }
    }

    function {
        name = MathFunction.ARCTAN
        arity = 1
        implementation = { args -> atan(args[0]) }
    }

    function {
        name = MathFunction.COSH
        arity = 1
        implementation = { args -> cosh(args[0]) }
    }

    function {
        name = MathFunction.SINH
        arity = 1
        implementation = { args -> sinh(args[0]) }
    }

    function {
        name = MathFunction.TANH
        arity = 1
        implementation = { args -> tanh(args[0]) }
    }

    function {
        name = MathFunction.ARCCOSH
        arity = 1
        implementation = { args -> acosh(args[0]) }
    }

    function {
        name = MathFunction.ARCSINH
        arity = 1
        implementation = { args -> asinh(args[0]) }
    }

    function {
        name = MathFunction.ARCTANH
        arity = 1
        implementation = { args -> atanh(args[0]) }
    }

    function {
        name = MathFunction.ABS
        arity = 1
        implementation = { args -> abs(args[0]) }
    }

    function {
        name = MathFunction.ROOT
        arity = 2
        implementation = { args -> KevalType.power(args[1], KevalType.divide(KevalType.ONE, args[0])) }
    }

    constant {
        name = Constant.PI
        value = KevalType.pi()
    }

    constant {
        name = Constant.E
        value = KevalType.e()
    }

}

val evaluator_degree_mode = evaluator
    .withFunction(MathFunction.COS, 1){ args -> cos(toRadians(args[0])) }
    .withFunction(MathFunction.SIN, 1){ args -> sin(toRadians(args[0])) }
    .withFunction(MathFunction.TAN, 1){ args -> tan(toRadians(args[0])) }
    .withFunction(MathFunction.ARCCOS, 1){ args -> toDegrees(acos(args[0])) }
    .withFunction(MathFunction.ARCSIN, 1){ args -> toDegrees(asin(args[0])) }
    .withFunction(MathFunction.ARCTAN, 1){ args -> toDegrees(atan(args[0])) }