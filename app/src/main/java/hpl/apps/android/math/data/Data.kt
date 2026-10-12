package hpl.apps.android.math.data

import ch.obermuhlner.math.big.BigDecimalMath
import com.notkamui.keval.Keval
import com.notkamui.keval.KevalNumber
import com.notkamui.keval.KevalOperator
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

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







object KevalType: KevalNumber<BigDecimal> {
    val roundingMode = RoundingMode.HALF_UP
    private lateinit var context: MathContext
    fun setMathContext(precision: Int, roundingMode: RoundingMode){
        context = MathContext(precision, roundingMode)
    }
    fun mathContext(): MathContext = if(this@KevalType::context.isInitialized) context else throw Error("KevalType.context not initialized.")

    val NEG_ONE: BigDecimal = BigDecimal.valueOf(-1L, 0)
    val ZERO: BigDecimal = BigDecimal.valueOf(0L, 0)
    val ONE: BigDecimal = BigDecimal.valueOf(1L, 0)
    val HUNDRED: BigDecimal = BigDecimal.valueOf(100L, 0)

    override fun isValidLiteral(token: String): Boolean = try {
        BigDecimal(token)
        true
    } catch (_: NumberFormatException) {
        false
    }
    override fun parseLiteral(token: String): BigDecimal = BigDecimal(token)
    override fun defaultResources(): Map<String, KevalOperator<BigDecimal>> = mapOf()

    fun pi(): BigDecimal = BigDecimalMath.pi(mathContext())
    fun e(): BigDecimal = BigDecimalMath.e(mathContext())

    override fun multiply(a: BigDecimal, b: BigDecimal): BigDecimal = a.multiply(b, mathContext())

    fun add(a: BigDecimal, b: BigDecimal): BigDecimal = a.add(b, mathContext())
    fun subtract(a: BigDecimal, b: BigDecimal): BigDecimal = a.subtract(b, mathContext())

    fun divide(a: BigDecimal, b: BigDecimal): BigDecimal = a.divide(b, mathContext())

    fun toDegrees(a: BigDecimal): BigDecimal = BigDecimalMath.toDegrees(a, mathContext())
    fun toRadians(a: BigDecimal): BigDecimal = BigDecimalMath.toRadians(a, mathContext())

    fun factorial(a: BigDecimal): BigDecimal = BigDecimalMath.factorial(a, mathContext())
    fun modulus(a: BigDecimal, b: BigDecimal): BigDecimal = a.remainder(b, mathContext())
    fun power(a: BigDecimal, b: BigDecimal): BigDecimal = BigDecimalMath.pow(a, b, mathContext())
    fun sqrt(a: BigDecimal): BigDecimal = BigDecimalMath.sqrt(a, mathContext())

    fun log(a: BigDecimal, b: BigDecimal): BigDecimal =
        BigDecimalMath.log(b, mathContext()).divide(BigDecimalMath.log(a, mathContext()), mathContext())
    fun log10(a: BigDecimal): BigDecimal = BigDecimalMath.log10(a, mathContext())
    fun ln(a: BigDecimal): BigDecimal = BigDecimalMath.log(a, mathContext())

    fun cos(a: BigDecimal): BigDecimal = BigDecimalMath.cos(a, mathContext())
    fun sin(a: BigDecimal): BigDecimal = BigDecimalMath.sin(a, mathContext())
    fun tan(a: BigDecimal): BigDecimal = BigDecimalMath.tan(a, mathContext())

    fun acos(a: BigDecimal): BigDecimal = BigDecimalMath.acos(a, mathContext())
    fun asin(a: BigDecimal): BigDecimal = BigDecimalMath.asin(a, mathContext())
    fun atan(a: BigDecimal): BigDecimal = BigDecimalMath.atan(a, mathContext())


    fun cosh(a: BigDecimal): BigDecimal = BigDecimalMath.cosh(a, mathContext())
    fun sinh(a: BigDecimal): BigDecimal = BigDecimalMath.sinh(a, mathContext())
    fun tanh(a: BigDecimal): BigDecimal = BigDecimalMath.tanh(a, mathContext())

    fun acosh(a: BigDecimal): BigDecimal = BigDecimalMath.acosh(a, mathContext())
    fun asinh(a: BigDecimal): BigDecimal = BigDecimalMath.asinh(a, mathContext())
    fun atanh(a: BigDecimal): BigDecimal = BigDecimalMath.atanh(a, mathContext())

    fun abs(a: BigDecimal): BigDecimal = if(a >= ZERO) a else a.multiply(NEG_ONE, mathContext())

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
        implementation = { a -> KevalType.sqrt(a) }
    }

    binaryOperator {
        symbol = Operator.SQRT
        precedence = 3
        isLeftAssociative = false
        implementation = { a, b -> KevalType.multiply(a, KevalType.sqrt(b)) }
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
        implementation = { args -> KevalType.ln(args[0]) }
    }

    function {
        name = MathFunction.LOG
        arity = 1
        implementation = { args -> KevalType.log10(args[0]) }
    }

    function {
        name = MathFunction.LOG_X
        arity = 2
        implementation = { args-> KevalType.log(args[0], args[1]) }
    }

    function {
        name = MathFunction.COS
        arity = 1
        implementation = { args -> KevalType.cos(args[0]) }
    }

    function {
        name = MathFunction.SIN
        arity = 1
        implementation = { args -> KevalType.sin(args[0]) }
    }

    function {
        name = MathFunction.TAN
        arity = 1
        implementation = { args -> KevalType.tan(args[0]) }
    }

    function {
        name = MathFunction.ARCCOS
        arity = 1
        implementation = { args -> KevalType.acos(args[0]) }
    }

    function {
        name = MathFunction.ARCSIN
        arity = 1
        implementation = { args -> KevalType.asin(args[0]) }
    }

    function {
        name = MathFunction.ARCTAN
        arity = 1
        implementation = { args -> KevalType.atan(args[0]) }
    }

    function {
        name = MathFunction.COSH
        arity = 1
        implementation = { args -> KevalType.cosh(args[0]) }
    }

    function {
        name = MathFunction.SINH
        arity = 1
        implementation = { args -> KevalType.sinh(args[0]) }
    }

    function {
        name = MathFunction.TANH
        arity = 1
        implementation = { args -> KevalType.tanh(args[0]) }
    }

    function {
        name = MathFunction.ARCCOSH
        arity = 1
        implementation = { args -> KevalType.acosh(args[0]) }
    }

    function {
        name = MathFunction.ARCSINH
        arity = 1
        implementation = { args -> KevalType.asinh(args[0]) }
    }

    function {
        name = MathFunction.ARCTANH
        arity = 1
        implementation = { args -> KevalType.atanh(args[0]) }
    }

    function {
        name = MathFunction.ABS
        arity = 1
        implementation = { args -> KevalType.abs(args[0]) }
    }

    function {
        name = MathFunction.ROOT
        arity = 2
        implementation = { args -> KevalType.power(args[1], KevalType.divide(KevalType.ONE, args[0])) }
    }

    function {
        name = Constant.PI
        arity = 0
        implementation = { KevalType.pi() }
    }

    function {
        name = Constant.E
        arity = 0
        implementation = { KevalType.e() }
    }

}

val evaluator_degree_mode = evaluator
    .withFunction(MathFunction.COS, 1){ args -> KevalType.cos(KevalType.toRadians(args[0])) }
    .withFunction(MathFunction.SIN, 1){ args -> KevalType.sin(KevalType.toRadians(args[0])) }
    .withFunction(MathFunction.TAN, 1){ args -> KevalType.tan(KevalType.toRadians(args[0])) }
    .withFunction(MathFunction.ARCCOS, 1){ args -> KevalType.toDegrees(KevalType.acos(args[0])) }
    .withFunction(MathFunction.ARCSIN, 1){ args -> KevalType.toDegrees(KevalType.asin(args[0])) }
    .withFunction(MathFunction.ARCTAN, 1){ args -> KevalType.toDegrees(KevalType.atan(args[0])) }