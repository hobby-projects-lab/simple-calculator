package hpl.apps.android.math.ui.screens.calculator

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.GetData
import hpl.apps.android.math.ui.components.button.MathButtonClass
import hpl.apps.android.math.ui.components.button.TextButtonClass
import hpl.apps.android.math.ui.components.button.VectorButtonClass

data class InputState(
    val expression: String,
    val cursorPosition: Int,
    val expressionChanged: Boolean,
    val preview: String,
    val previewChanged: Boolean
)

class CalculatorKeyPadSwitch{
    val num = 0
    val func = 1
    val funcInv = 2
    val other = 3
    val controller = mutableIntStateOf(num)
    fun switchNum(){ controller.intValue = num }
    fun switchFunc(){ controller.intValue = func }
    fun switchFuncInv(){ controller.intValue = funcInv }
    fun switchOther(){ controller.intValue = other }
}

class CalculatorViewModel: ViewModel(){
    val inputState = mutableStateOf(
        InputState(
            "",
            0,
            false,
                "",
            false
        )
    )

    val degreeMode = mutableStateOf(false)

    val keyPadSwitch = CalculatorKeyPadSwitch()

    val historySize = mutableIntStateOf(0)
    val history = mutableListOf<HistoryItem>()

    private val evaluate = GetData.expressionHandler()::evaluate
    private val operators = GetData.operators()
    private val constants = GetData.constants()
    private val functions = GetData.functions()
    private val expressions = GetData.expressions()

    private fun clearAll(){
        inputState.value = InputState(
            "",
            0,
            true,
            "",
            true
        )
        history.clear()
        historySize.intValue = history.size
    }

    private val percent = (TextButtonClass.PERCENT.displayText?: TextButtonClass.PERCENT.text)[0]
    private val mul = (TextButtonClass.MUL.displayText?: TextButtonClass.MUL.text)[0]
    private val div = (TextButtonClass.DIV.displayText?: TextButtonClass.DIV.text)[0]
    private val sqrt = (TextButtonClass.SQRT.displayText?: TextButtonClass.SQRT.text)[0]
    private val pi = (TextButtonClass.PI.displayText?: TextButtonClass.PI.text)[0]

    private val mod0 = (TextButtonClass.MOD.displayText?: TextButtonClass.MOD.text)[0]
    private val mod1 = (TextButtonClass.MOD.displayText?: TextButtonClass.MOD.text)[1]
    private val mod2 = (TextButtonClass.MOD.displayText?: TextButtonClass.MOD.text)[2]

    private val logX0 = (TextButtonClass.LOG_X.displayText?: TextButtonClass.LOG_X.text)[0]
    private val logX1 = (TextButtonClass.LOG_X.displayText?: TextButtonClass.LOG_X.text)[1]
    private val logX2 = (TextButtonClass.LOG_X.displayText?: TextButtonClass.LOG_X.text)[2]
    private val logX3 = (TextButtonClass.LOG_X.displayText?: TextButtonClass.LOG_X.text)[3]
    private fun processExpression(expression: String): String{
        val l2 = expression.length-2
        val l3 = expression.length-3

        var i = 0
        var c: Char
        return buildString{
            while (i<expression.length){
                c = expression[i]
                when{
                    c == percent -> {
                        append(operators.PERCENT)
                        i++
                    }
                    c == mul -> {
                        append(operators.MUL)
                        i++
                    }
                    c == div -> {
                        append(operators.DIV)
                        i++
                    }
                    c == sqrt -> {
                        append(operators.SQRT)
                        i++
                    }
                    c == pi -> {
                        append(constants.PI)
                        i++
                    }
                    i<l2 &&
                            c == mod0 &&
                            expression[i+1] == mod1 &&
                            expression[i+2] == mod2 -> {
                        append(operators.MOD)
                        i += 3
                    }
                    i<l3 &&
                            c == logX0 &&
                            expression[i+1] == logX1 &&
                            expression[i+2] == logX2 &&
                            expression[i+3] == logX3 -> {
                        append(functions.LOG_X)
                        i += 3
                    }
                    else -> {
                        append(c)
                        i++
                    }
                }
            }
        }
    }
    private fun evaluateExpression(processedExpression: String, context: Context): String{
        val result = evaluate(processedExpression, degreeMode.value)
        return result ?: getString(context, R.string.error)
    }

    private val nonValidExpressionEndings = setOf(
        operators.MOD,
        operators.DIV,
        operators.MUL,
        operators.SQRT,
        expressions.SCI_NOTATION,
        operators.ADD,
        operators.POW,
        operators.SUB,
        '(',
        '['
    )
    private val nonValidExpressionBeginnings = setOf(
        operators.MOD,
        operators.DIV,
        operators.MUL,
        expressions.SCI_NOTATION,
        operators.POW,
        operators.FACT,
        operators.PERCENT,
        ')',
        ']'
    )
    private val decimalRegex = Regex("${operators.SUB}?\\d+(\\.\\d*)?")
    private fun previewResult(expression: String, context: Context): String?{
        var unnecessary = expression.isEmpty() || expression.matches(decimalRegex)
        if(unnecessary) return null
        val processedExpression = processExpression(expression)
        unnecessary = processedExpression.first() in nonValidExpressionBeginnings || processedExpression.last() in nonValidExpressionEndings
        if(unnecessary) return null
        val result = evaluateExpression(processedExpression, context)
        return result
    }

    fun setExpression(newExpression: String, context: Context){
        inputState.value = InputState(
            newExpression,
            newExpression.length,
            true,
            previewResult(newExpression, context)?: "",
            true
        )
    }

    fun handleClick(button: MathButtonClass, context: Context){
        when(button){
            TextButtonClass.EQUAL ->{
                if(inputState.value.expression.isNotEmpty()) {
                    val result = inputState.value.preview.ifEmpty {
                        evaluateExpression(
                            processExpression(inputState.value.expression),
                            context
                        )
                    }
                    if(result != getString(context, R.string.error)){
                        history.add(HistoryItem(historySize.intValue+1, inputState.value.expression))
                        historySize.intValue = history.size
                    }
                    inputState.value = InputState(
                        result,
                        result.length,
                        true,
                        "",
                        true
                    )
                }
            }
            VectorButtonClass.BACKSPACE ->{
                if(inputState.value.cursorPosition>0) {
                    val newExpression = inputState.value.expression.removeRange(
                        inputState.value.cursorPosition - 1..<inputState.value.cursorPosition
                    )
                    inputState.value = InputState(
                        newExpression,
                        inputState.value.cursorPosition-1,
                        true,
                        previewResult(newExpression, context)?: "",
                        true
                    )
                }
            }
            TextButtonClass.CLEAR ->{
                inputState.value = InputState(
                    "",
                    0,
                    true,
                    "",
                    true
                )
            }
            TextButtonClass.CURSOR_FORWARD ->{
                inputState.value = inputState.value.copy(
                    cursorPosition = maxOf(
                        0,
                        inputState.value.cursorPosition-1
                    ),
                    expressionChanged = false,
                    previewChanged = false
                )
            }
            TextButtonClass.CURSOR_BACK ->{
                inputState.value = inputState.value.copy(
                    cursorPosition = minOf(
                        inputState.value.expression.length,
                        inputState.value.cursorPosition+1
                    ),
                    expressionChanged = false,
                    previewChanged = false
                )
            }
            TextButtonClass.QUICK_CURSOR_FORWARD ->{
                inputState.value = inputState.value.copy(
                    cursorPosition = 0,
                    expressionChanged = false,
                    previewChanged = false
                )
            }
            TextButtonClass.QUICK_CURSOR_BACK ->{
                inputState.value = inputState.value.copy(
                    cursorPosition = inputState.value.expression.length,
                    expressionChanged = false,
                    previewChanged = false
                )
            }
            TextButtonClass.DEG ->{
                degreeMode.value = !degreeMode.value
                if(inputState.value.expression.isNotEmpty()) {
                    val preview = previewResult(inputState.value.expression, context)
                    inputState.value = inputState.value.copy(
                        expressionChanged = false,
                        preview = preview?: "",
                        previewChanged = true
                    )
                }
            }
            TextButtonClass.RAD ->{
                degreeMode.value = !degreeMode.value
                if(inputState.value.expression.isNotEmpty()) {
                    val preview = previewResult(inputState.value.expression, context)
                    inputState.value = inputState.value.copy(
                        expressionChanged = false,
                        preview = preview?: "",
                        previewChanged = true
                    )
                }
            }
            else -> {
                if(button is TextButtonClass){
                    val extraString = button.displayText?:button.text
                    val newExpression = inputState.value.expression.substring(0, inputState.value.cursorPosition)+
                            extraString+
                            inputState.value.expression.substring(inputState.value.cursorPosition)
                    inputState.value = InputState(
                        newExpression,
                        inputState.value.cursorPosition+extraString.length,
                        true,
                        previewResult(newExpression, context)?: "",
                        previewChanged = true
                    )
                }
            }
        }
    }

    fun handleLongClick(button: MathButtonClass){
        when(button){
            TextButtonClass.CLEAR ->{
                clearAll()
            }
        }
    }
}