package hpl.apps.android.math.data

import hpl.apps.android.math.utils.MeasurementUnit
import java.math.BigDecimal

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


    private fun evaluateInternal(expression: String, precision: Int, degreeMode: Boolean): String?{
        val result = try {
            if(degreeMode) {
                evaluator_degree_mode.eval(expression)
            }else{
                evaluator.eval(expression)
            }
        }catch (_: Exception){
            return null
        }
        return result.clean(precision)
    }
    fun evaluate(expression: String, precision: Int, degreeMode: Boolean): String?{
        val processedExpression = process(expression)
        return computation(
            processedExpression,
            precision
        ) { evaluateInternal(it, precision, degreeMode) }
    }


    private fun convertInternal(number: BigDecimal, precision: Int, from: MeasurementUnit, to: MeasurementUnit): String?{
        val result = try {
            to.fromBase(from.toBase(number))
        }catch (_: Exception){
            return null
        }
        return result.clean(precision)
    }
    fun convert(numberAsString: String, precision: Int, from: MeasurementUnit, to: MeasurementUnit): String?{
        val number = processConverterExpression(numberAsString) ?: return null
        return computation(
            number,
            precision
        ){ convertInternal(it, precision, from, to) }
    }

}


private fun <T> computation(input: T, precision: Int, compute: (T)-> String?): String?{
    var internalPrecision = 2*precision+10
    var r1: String
    var r2: String
    while (true){
        KevalType.setMathContext(internalPrecision, KevalType.roundingMode)
        r1 = compute(input)?: return null
        KevalType.setMathContext(2*internalPrecision, KevalType.roundingMode)
        r2 = compute(input)?: return null
        if(r1 == r2){
            return r1
        }
        internalPrecision *= 2
    }
}

private fun BigDecimal.clean(precision: Int): String =
    this.setScale(precision, KevalType.roundingMode)
        .stripTrailingZeros()
        .toPlainString()