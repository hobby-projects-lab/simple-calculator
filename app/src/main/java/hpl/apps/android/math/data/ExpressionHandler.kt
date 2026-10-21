package hpl.apps.android.math.data

import hpl.apps.android.math.utils.MeasurementUnit
import kotlinx.coroutines.currentCoroutineContext
import java.math.BigDecimal
import java.math.MathContext

object ExpressionHandler {
    private const val ALLOWED_TRAILING_OPERATORS = "${Operator.ADD}"+
            "${Operator.SUB}"+
            "${Operator.MUL}"+
            "${Operator.DIV}"+
            "${Operator.POW}"

    private const val PERCENT1 = "${Operator.PERCENT}1"
    private fun process(expression: String): String{
        var processedExpression = expression

        while(processedExpression.isNotEmpty() && ALLOWED_TRAILING_OPERATORS.contains(processedExpression.last())){
            processedExpression = processedExpression.dropLast(1)
        }

        val parenthesesBalance = processedExpression.count{it == '('} - processedExpression.count{it == ')'}
        if(parenthesesBalance > 0){
            processedExpression += ")".repeat(parenthesesBalance)
        }else if(parenthesesBalance < 0){
            processedExpression = "${"(".repeat(-parenthesesBalance)}$processedExpression"
        }


        /*
        - Replacing '[' with '(' and '](' with ',' to change expressions like
        log[3](9) into log(3,9).
        - Adding 1 as right operand for percent operator
        if a right operand is not present in order to allow expressions like 1+3%(7-2)
        - Replacing the scientific notation symbol with its value
        - Adding () to constants identifiers because they are actually functions
        */

        val l1 = processedExpression.length-1

        var i = 0
        var c: Char
        processedExpression = buildString {
            while (i<processedExpression.length){
                c = processedExpression[i]
                when{
                    c == '[' -> {
                        append('(')
                        i++
                    }
                    c == ']' && (i<l1 && processedExpression[i+1] == '(' )-> {
                        append(',')
                        i += 2
                    }
                    c == Operator.PERCENT && (i == l1 || !processedExpression[i+1].isLetterOrDigit()) -> {
                        append(PERCENT1)
                        i++
                    }
                    c == Expression.SCI_NOTATION ->{
                        append(Expression.SCI_NOTATION_VALUE)
                        i++
                    }
                    c.isLetter() &&(i == l1 || (!processedExpression[i+1].isLetter()&& processedExpression[i+1] != '(' && processedExpression[i+1] != '[')) ->{
                        append("${c}()")
                        i++
                    }
                    else -> {
                        append(c)
                        i++
                    }
                }
            }
        }

        return processedExpression
    }

    private fun processConverterExpression(expression: String): BigDecimal?{
        val result = try {
            expression.toBigDecimal()
        }catch (_: Exception){
            null
        }
        return result
    }


    suspend fun evaluate(expression: String, precision: Int, degreeMode: Boolean): String?{
        val processedExpression = process(expression)
        val result = try{
            compute(precision) {
                if(degreeMode) {
                    evaluator_degree_mode.eval(processedExpression)
                }else{
                    evaluator.eval(processedExpression)
                }
            }
        }catch (_: Exception){
            null
        }
        return result?.clean()
    }


    suspend fun convert(numberAsString: String, precision: Int, from: MeasurementUnit, to: MeasurementUnit): String?{
        val number = processConverterExpression(numberAsString) ?: return null
        val result = try {
            compute(precision) { to.fromBase(from.toBase(number)) }
        }catch (_: Exception){
            null
        }
        return result?.clean()
    }

}

private suspend fun compute(precision: Int, func: ()-> BigDecimal): BigDecimal{
    KevalType.setCoroutineContext(currentCoroutineContext())

    var internalPrecision1 = UserPreferencesRepository.getInternalPrecision()
    var internalPrecision2: Int

    KevalType.setMathContext(MathContext(internalPrecision1, UserPreferencesRepository.roundingMode))

    var result1 = func().setScale(precision, UserPreferencesRepository.roundingMode)
    var result2: BigDecimal
    while (KevalType.isCoroutineContextActive()){
        if(internalPrecision1 == UserPreferencesRepository.MAX_INTERNAL_PRECISION) CalculatorException.limitReached()
        internalPrecision2 = UserPreferencesRepository.increaseInternalPrecision(internalPrecision1)
        KevalType.setMathContext(MathContext(internalPrecision2, UserPreferencesRepository.roundingMode))

        result2 = func().setScale(precision, UserPreferencesRepository.roundingMode)
        if(result1 == result2) break
        result1 = result2
        internalPrecision1 = internalPrecision2
    }
    if(!UserPreferencesRepository.isInRange(result1)) CalculatorException.resultTooLarge()
    return result1
}


private val cleanRange = BigDecimal.valueOf(-1L, -10)..BigDecimal.valueOf(1L, -10)
private val cleanRange2 = BigDecimal.valueOf(-1L, 10)..BigDecimal.valueOf(1L, 10)
private fun BigDecimal.clean(): String {
    val x = this.stripTrailingZeros()
    return if(x !in cleanRange || x in cleanRange2) x.toString() else x.toPlainString()
}