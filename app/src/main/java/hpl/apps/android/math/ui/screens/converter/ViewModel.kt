package hpl.apps.android.math.ui.screens.converter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import hpl.apps.android.math.ui.GetData
import hpl.apps.android.math.ui.components.button.MathButtonClass
import hpl.apps.android.math.ui.components.button.TextButtonClass
import hpl.apps.android.math.ui.components.button.VectorButtonClass
import hpl.apps.android.math.utils.Dimension
import hpl.apps.android.math.utils.MeasurementUnit
import hpl.apps.android.math.utils.baseUnit2Index
import hpl.apps.android.math.utils.baseUnitIndex
import hpl.apps.android.math.utils.oops


data class ConverterDisplayState(
    val expression: String,
    val cursorPosition: Int,
    val expressionChanged: Boolean,
    val selectedUnit1: MeasurementUnit,
    val selectedUnit2: MeasurementUnit,
    val result: String
)

class ConverterViewModel: ViewModel(){
    var selectedDimension = units.keys.first()
        private set

    private fun getUnitsForCurrentDimension(dimension: Dimension): Map<String, MeasurementUnit>{
        if(!units.containsKey(dimension)) oops("Units not found for the dimension $dimension.")
        return units[dimension]!!
    }

    private fun initializeUnit1(dimension: Dimension, unitsForCurrentDimension: Map<String, MeasurementUnit>): MeasurementUnit {
        if(!unitsForCurrentDimension.containsKey(baseUnitIndex)) oops("Unit $baseUnitIndex not found for the dimension $dimension.")
        return unitsForCurrentDimension[baseUnitIndex]!!
    }

    private fun initializeUnit2(dimension: Dimension, unitsForCurrentDimension: Map<String, MeasurementUnit>): MeasurementUnit {
        if(!unitsForCurrentDimension.containsKey(baseUnit2Index)) oops("Unit $baseUnit2Index not found for the dimension $dimension.")
        return unitsForCurrentDimension[baseUnit2Index]!!
    }

    private fun initializeState(): MutableState<ConverterDisplayState> {
        val unitsForCurrentDimension = getUnitsForCurrentDimension(selectedDimension)
        return mutableStateOf(
            ConverterDisplayState(
                "",
                0,
                false,
                initializeUnit1(selectedDimension, unitsForCurrentDimension),
                initializeUnit2(selectedDimension, unitsForCurrentDimension),
                ""
            )
        )
    }

    val state = initializeState()

    fun setUnit1(unit: MeasurementUnit){
        state.value = state.value.copy(
            selectedUnit1 = unit,
            expressionChanged = false,
            result = convert(state.value.expression, unit, state.value.selectedUnit2)?: ""
        )
    }

    fun setUnit2(unit: MeasurementUnit){
        state.value = state.value.copy(
            selectedUnit2 = unit,
            expressionChanged = false,
            result = convert(state.value.expression, state.value.selectedUnit1, unit)?: ""
        )
    }

    private fun clearState(){
        state.value = ConverterDisplayState(
            "",
            0,
            true,
            state.value.selectedUnit1,
            state.value.selectedUnit2,
            ""
        )
    }

    fun reset(dimension: Dimension){
        selectedDimension = dimension
        state.value = initializeState().value
    }

    private val converterFunction = GetData.expressionHandler()::convert
    private fun convert(numberAsString: String, from: MeasurementUnit, to: MeasurementUnit): String? =
        converterFunction(numberAsString, from, to)

    fun handleSwap(){
        val selectedUnit1 = state.value.selectedUnit2
        val selectedUnit2 = state.value.selectedUnit1
        state.value = ConverterDisplayState(
            state.value.result,
            state.value.result.length,
            true,
            selectedUnit1,
            selectedUnit2,
            convert(state.value.result, selectedUnit1, selectedUnit2)?: ""
        )
    }

    fun handleClick(button: MathButtonClass, from: MeasurementUnit, to: MeasurementUnit){
        when(button){
            VectorButtonClass.BACKSPACE ->{
                val minCursorPosition = if(state.value.expression.startsWith(TextButtonClass.SIGN.displayText?: TextButtonClass.SIGN.text)) 1 else 0
                if(state.value.cursorPosition>minCursorPosition) {
                    val newExpression = state.value.expression.removeRange(
                        state.value.cursorPosition - 1..<state.value.cursorPosition
                    )
                    state.value = ConverterDisplayState(
                        newExpression,
                        state.value.cursorPosition-1,
                        true,
                        state.value.selectedUnit1,
                        state.value.selectedUnit2,
                        convert(newExpression, from, to)?: ""
                    )
                }
            }
            TextButtonClass.CLEAR ->{
                clearState()
            }
            TextButtonClass.CURSOR_FORWARD ->{
                val minCursorPosition = if(state.value.expression.startsWith(TextButtonClass.SIGN.displayText?: TextButtonClass.SIGN.text)) 1 else 0
                state.value = state.value.copy(
                    cursorPosition = maxOf(
                        minCursorPosition,
                        state.value.cursorPosition-1
                    ),
                    expressionChanged = false
                )
            }
            TextButtonClass.CURSOR_BACK ->{
                state.value = state.value.copy(
                    cursorPosition = minOf(
                        state.value.expression.length,
                        state.value.cursorPosition+1
                    ),
                    expressionChanged = false
                )
            }
            TextButtonClass.QUICK_CURSOR_FORWARD ->{
                val minCursorPosition = if(state.value.expression.startsWith(TextButtonClass.SIGN.displayText?: TextButtonClass.SIGN.text)) 1 else 0
                state.value = state.value.copy(
                    cursorPosition = minCursorPosition,
                    expressionChanged = false
                )
            }
            TextButtonClass.QUICK_CURSOR_BACK ->{
                state.value = state.value.copy(
                    cursorPosition = state.value.expression.length,
                    expressionChanged = false
                )
            }

            TextButtonClass.SIGN ->{
                var newCursorPosition: Int
                val newExpression = if(state.value.expression.startsWith(TextButtonClass.SIGN.displayText?: TextButtonClass.SIGN.text)){
                    newCursorPosition = state.value.cursorPosition-1
                    state.value.expression.removeRange(0..<1)
                }else{
                    newCursorPosition = state.value.cursorPosition+1
                    (TextButtonClass.SIGN.displayText?: TextButtonClass.SIGN.text)+state.value.expression
                }
                state.value = state.value.copy(
                    expression = newExpression,
                    cursorPosition = newCursorPosition,
                    expressionChanged = true,
                    result = convert(newExpression, from, to)?: ""
                )
            }
            else -> {
                if(button is TextButtonClass){
                    val extraString = button.displayText?:button.text
                    val newExpression = state.value.expression.substring(0, state.value.cursorPosition)+
                            extraString+
                            state.value.expression.substring(state.value.cursorPosition)
                    state.value = ConverterDisplayState(
                        newExpression,
                        state.value.cursorPosition+extraString.length,
                        true,
                        state.value.selectedUnit1,
                        state.value.selectedUnit2,
                        convert(newExpression, from, to)?: ""
                    )
                }
            }
        }
    }
}