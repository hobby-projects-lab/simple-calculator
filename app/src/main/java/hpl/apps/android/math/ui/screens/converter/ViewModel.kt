package hpl.apps.android.math.ui.screens.converter

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hpl.apps.android.math.CalculatorApplication
import hpl.apps.android.math.R
import hpl.apps.android.math.data.UserPreferencesRepository
import hpl.apps.android.math.ui.GetData
import hpl.apps.android.math.ui.components.button.MathButtonClass
import hpl.apps.android.math.ui.components.button.TextButtonClass
import hpl.apps.android.math.ui.components.button.VectorButtonClass
import hpl.apps.android.math.utils.Dimension
import hpl.apps.android.math.utils.MeasurementUnit
import hpl.apps.android.math.utils.baseUnit2Index
import hpl.apps.android.math.utils.baseUnitIndex
import hpl.apps.android.math.utils.oops
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


data class ConverterDisplayState(
    val expression: String,
    val cursorPosition: Int,
    val expressionChanged: Boolean,
    val selectedUnit1: MeasurementUnit,
    val selectedUnit2: MeasurementUnit,
    val result: String
)

class ConverterViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val defaultDispatcher: CoroutineDispatcher
): ViewModel(){
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

    private fun initializeState(): MutableStateFlow<ConverterDisplayState> {
        val unitsForCurrentDimension = getUnitsForCurrentDimension(selectedDimension)
        return MutableStateFlow(
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

    fun setUnit1(unit: MeasurementUnit, context: Context){
        setLoadingState(
            expression = state.value.expression,
            cursorPosition = state.value.cursorPosition,
            expressionChanged = false,
            selectedUnit1 = unit,
            selectedUnit2 = state.value.selectedUnit2,
            context = context
        )
        startNewJob {
            state.value = state.value.copy(
                result = convert(state.value.expression, state.value.selectedUnit1, unit)?: ""
            )
        }
    }

    fun setUnit2(unit: MeasurementUnit, context: Context){
        setLoadingState(
            expression = state.value.expression,
            cursorPosition = state.value.cursorPosition,
            expressionChanged = false,
            selectedUnit1 = state.value.selectedUnit1,
            selectedUnit2 = unit,
            context = context
        )
        startNewJob {
            state.value = state.value.copy(
                result = convert(state.value.expression, state.value.selectedUnit1, unit)?: ""
            )
        }
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
    private suspend fun convert(numberAsString: String, from: MeasurementUnit, to: MeasurementUnit): String? =
        withContext(defaultDispatcher) {
            converterFunction(
                numberAsString,
                userPreferencesRepository.precision.first(),
                from,
                to
            )
        }

    fun handleSwap(context: Context){
        val selectedUnit1 = state.value.selectedUnit2
        val selectedUnit2 = state.value.selectedUnit1
        setLoadingState(
            expression = state.value.result,
            cursorPosition = state.value.result.length,
            expressionChanged = true,
            selectedUnit1 = selectedUnit1,
            selectedUnit2 = selectedUnit2,
            context = context
        )
        startNewJob {
            state.value = state.value.copy(
                result = convert(state.value.expression, state.value.selectedUnit1, state.value.selectedUnit2)?: ""
            )
        }
    }

    fun handleClick(button: MathButtonClass, from: MeasurementUnit, to: MeasurementUnit, context: Context){
        when(button){
            VectorButtonClass.BACKSPACE ->{
                val minCursorPosition = if(state.value.expression.startsWith(TextButtonClass.SIGN.displayText?: TextButtonClass.SIGN.text)) 1 else 0
                if(state.value.cursorPosition>minCursorPosition) {
                    val newExpression = state.value.expression.removeRange(
                        state.value.cursorPosition - 1..<state.value.cursorPosition
                    )
                    setLoadingState(
                        expression = newExpression,
                        cursorPosition = state.value.cursorPosition-1,
                        expressionChanged = true,
                        selectedUnit1 = state.value.selectedUnit1,
                        selectedUnit2 = state.value.selectedUnit2,
                        context = context
                    )
                    startNewJob {
                        state.value = state.value.copy(
                            result = convert(newExpression, from, to)?: ""
                        )
                    }
                }
            }
            TextButtonClass.CLEAR ->{
                cancelJob()
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
                setLoadingState(
                    expression = newExpression,
                    cursorPosition = newCursorPosition,
                    expressionChanged = true,
                    selectedUnit1 = state.value.selectedUnit1,
                    selectedUnit2 = state.value.selectedUnit2,
                    context = context
                )
                startNewJob {
                    state.value = state.value.copy(
                        result = convert(newExpression, from, to)?: ""
                    )
                }
            }
            else -> {
                if(button is TextButtonClass){
                    val extraString = button.displayText?:button.text
                    val newExpression = state.value.expression.substring(0, state.value.cursorPosition)+
                            extraString+
                            state.value.expression.substring(state.value.cursorPosition)
                    setLoadingState(
                        expression = newExpression,
                        cursorPosition = state.value.cursorPosition+extraString.length,
                        expressionChanged = true,
                        selectedUnit1 = state.value.selectedUnit1,
                        selectedUnit2 = state.value.selectedUnit2,
                        context = context
                    )
                    startNewJob {
                        state.value = state.value.copy(
                            result = convert(newExpression, from, to)?: ""
                        )
                    }
                }
            }
        }
    }


    private lateinit var job: Job
    private fun cancelJob(){ if(this@ConverterViewModel::job.isInitialized) job.cancel() }
    private fun startNewJob(callBack: suspend ()-> Unit){
        cancelJob()
        job = viewModelScope.launch{callBack()}
    }
    private fun setLoadingState(
        expression: String,
        cursorPosition: Int,
        expressionChanged: Boolean,
        selectedUnit1: MeasurementUnit,
        selectedUnit2: MeasurementUnit,
        context: Context
    ){
        state.value = ConverterDisplayState(
            expression,
            cursorPosition,
            expressionChanged,
            selectedUnit1,
            selectedUnit2,
            getString(context, R.string.computing)
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CalculatorApplication)
                ConverterViewModel(application.userPreferencesRepository, Dispatchers.Default)
            }
        }
    }
}