package hpl.apps.android.math.data

import hpl.apps.android.math.utils.MeasurementUnit
import java.util.Locale

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
        - Preventing identifiers from having numbers by inserting multiplication sign
        for expressions like PI3log to become PI*3*log
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
                    c.isLetter() &&(i<l1 && processedExpression[i+1].isDigit()) ->{
                        append("${c}${Operator.MUL}")
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

    private fun processConverterExpression(expression: String): Double?{
        val result = try {
            expression.toDouble()
        }catch (_: Exception){
            null
        }
        return result
    }

    fun evaluate(expression: String, degreeMode: Boolean): String?{
        val processedExpression = process(expression)
        val result = try {
            if(degreeMode) {
                evaluator_degree_mode.eval(processedExpression)
            }else{
                evaluator.eval(processedExpression)
            }
        }catch (_: Exception){
            return null
        }
        return result.roundAndToString()
    }

    fun convert(numberAsString: String, from: MeasurementUnit, to: MeasurementUnit): String?{
        val number = processConverterExpression(numberAsString) ?: return null
        val result = try {
            to.fromBase(from.toBase(number))
        }catch (_: Exception){
            return null
        }
        return result.roundAndToString()
    }

}



private const val POSITIVE_INFINITY = "∞"
private const val NEGATIVE_INFINITY = "-∞"

private const val TEN_TO_THE_10 = 1e10
private const val NEGATIVE_TEN_TO_THE_10 = -1e10

private fun Double.roundAndToString(): String {
    return when {
        this.isFinite() && this > NEGATIVE_TEN_TO_THE_10 && this < TEN_TO_THE_10 -> {
            String
                .format(Locale.ENGLISH, "%.10f", this)
                .dropLastWhile { it == '0' }
                .dropLastWhile { it == '.' }
        }
        this == Double.POSITIVE_INFINITY -> POSITIVE_INFINITY
        this == Double.NEGATIVE_INFINITY -> NEGATIVE_INFINITY
        else -> {
            this.toString()
        }
    }
}