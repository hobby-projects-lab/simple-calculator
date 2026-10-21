package hpl.apps.android.math.data

import androidx.annotation.StringRes
import ch.obermuhlner.math.big.BigDecimalMath
import com.notkamui.keval.Keval
import com.notkamui.keval.KevalNumber
import com.notkamui.keval.KevalOperator
import hpl.apps.android.math.R
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import java.math.BigDecimal
import java.math.MathContext
import kotlin.coroutines.CoroutineContext

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






object CalculatorException{
    @StringRes var id: Int = R.string.error
        private set

    private fun throwException(@StringRes value: Int){
        id = value
        throw Exception()
    }

    fun reset(){
        id = R.string.error
    }
    fun resultTooLarge(){
        throwException(R.string.result_too_large_error)
    }
    fun expressionWithTooLargeValues(){
        throwException(R.string.expression_with_too_large_values_error)
    }
    fun factorialOfANonInteger(){
        throwException(R.string.factorial_of_a_non_integer_error)
    }
    fun factorialOfANegativeNumber(){
        throwException(R.string.factorial_of_a_negative_number_error)
    }

    fun tempBelowAbsZero(){
        throwException(R.string.temp_below_abs_zero_error)
    }

    fun limitReached(){
        throwException(R.string.app_limit_reached_error)
    }
}
object KevalType: KevalNumber<BigDecimal> {
    override fun isValidLiteral(token: String): Boolean = try {
        BigDecimal(token)
        true
    } catch (_: NumberFormatException) {
        false
    }
    override fun parseLiteral(token: String): BigDecimal = BigDecimal(token)
    override fun defaultResources(): Map<String, KevalOperator<BigDecimal>> = mapOf()

    private lateinit var coroutineContext: CoroutineContext
    private fun getCoroutineContext(): CoroutineContext = if(this@KevalType::coroutineContext.isInitialized) coroutineContext else throw Error("KevalType.coroutineContext not initialized.")
    fun setCoroutineContext(value: CoroutineContext){ coroutineContext = value }
    fun isCoroutineContextActive(): Boolean = getCoroutineContext().isActive

    val NEG_ONE: BigDecimal = BigDecimal.valueOf(-1L, 0)
    private val ZERO: BigDecimal = BigDecimal.valueOf(0L, 0)
    val ONE: BigDecimal = BigDecimal.valueOf(1L, 0)
    val HUNDRED: BigDecimal = BigDecimal.valueOf(100L, 0)

    private lateinit var mathContext: MathContext
    private fun getMathContext(): MathContext = if(this@KevalType::mathContext.isInitialized) mathContext else throw Error("KevalType.mathContext not initialized.")
    fun setMathContext(value: MathContext){ mathContext = value }

    fun compute(values: List<BigDecimal>, func: (List<BigDecimal>)-> BigDecimal): BigDecimal{
        getCoroutineContext().ensureActive()
        for(i in values){
            if (!UserPreferencesRepository.isInRange(i)) CalculatorException.expressionWithTooLargeValues()
        }
        val result = func(values)
        if(!UserPreferencesRepository.isInRange(result)) CalculatorException.expressionWithTooLargeValues()
        return result
    }

    fun pi(): BigDecimal = BigDecimalMath.pi(getMathContext())
    fun e(): BigDecimal = BigDecimalMath.e(getMathContext())

    override fun multiply(a: BigDecimal, b: BigDecimal): BigDecimal = compute(listOf(a, b)){ it[0].multiply(it[1], getMathContext()) }
    fun add(a: BigDecimal, b: BigDecimal): BigDecimal = compute(listOf(a, b)){ it[0].add(it[1], getMathContext()) }
    fun subtract(a: BigDecimal, b: BigDecimal): BigDecimal = compute(listOf(a, b)){ it[0].subtract(it[1], getMathContext()) }
    fun divide(a: BigDecimal, b: BigDecimal): BigDecimal = compute(listOf(a, b)){ it[0].divide(it[1], getMathContext()) }
    fun identity(a: BigDecimal): BigDecimal = compute(listOf(a)){ it[0] }

    fun toDegrees(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.toDegrees(it[0], getMathContext()) }
    fun toRadians(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.toRadians(it[0], getMathContext()) }

    fun factorial(a: BigDecimal): BigDecimal = compute(listOf(a)){
        if(it[0] > UserPreferencesRepository.MAX_FACTORIAL_INPUT) CalculatorException.expressionWithTooLargeValues()
        if(it[0].toBigInteger().toBigDecimal().compareTo(it[0]) != 0) CalculatorException.factorialOfANonInteger()
        if(it[0] < ZERO) CalculatorException.factorialOfANegativeNumber()
        BigDecimalMath.factorial(it[0], getMathContext())
    }
    fun modulus(a: BigDecimal, b: BigDecimal): BigDecimal = compute(listOf(a, b)) { it[0].remainder(it[1], getMathContext()) }
    fun power(a: BigDecimal, b: BigDecimal): BigDecimal = compute(listOf(a, b)){ BigDecimalMath.pow(it[0], it[1], getMathContext()) }
    fun sqrt(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.sqrt(it[0], getMathContext()) }

    fun log(a: BigDecimal, b: BigDecimal): BigDecimal = compute(listOf(a, b)){
        BigDecimalMath.log(it[1], getMathContext()).divide(BigDecimalMath.log(it[0], getMathContext()), getMathContext())
    }
    fun log10(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.log10(it[0], getMathContext()) }
    fun ln(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.log(it[0], getMathContext()) }

    fun cos(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.cos(it[0], getMathContext()) }
    fun sin(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.sin(it[0], getMathContext()) }
    fun tan(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.tan(it[0], getMathContext()) }

    fun acos(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.acos(it[0], getMathContext()) }
    fun asin(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.asin(it[0], getMathContext()) }
    fun atan(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.atan(it[0], getMathContext()) }


    fun cosh(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.cosh(it[0], getMathContext()) }
    fun sinh(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.sinh(it[0], getMathContext()) }
    fun tanh(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.tanh(it[0], getMathContext()) }

    fun acosh(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.acosh(it[0], getMathContext()) }
    fun asinh(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.asinh(it[0], getMathContext()) }
    fun atanh(a: BigDecimal): BigDecimal = compute(listOf(a)){ BigDecimalMath.atanh(it[0], getMathContext()) }

    fun abs(a: BigDecimal): BigDecimal = compute(listOf(a)){ if(it[0] >= ZERO) it[0] else it[0].multiply(NEG_ONE) }

}
val evaluator = Keval.create(KevalType){

    unaryOperator {
        symbol = Operator.ADD
        isPrefix = true
        implementation = {a-> KevalType.identity(a) }
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
        implementation = { KevalType.pi() }
    }

    function {
        name = Constant.E
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