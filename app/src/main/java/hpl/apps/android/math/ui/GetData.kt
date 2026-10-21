package hpl.apps.android.math.ui

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat.getString
import hpl.apps.android.math.data.CalculatorException
import hpl.apps.android.math.data.Constant
import hpl.apps.android.math.data.Expression
import hpl.apps.android.math.data.ExpressionHandler
import hpl.apps.android.math.data.MathFunction
import hpl.apps.android.math.data.Operator
import hpl.apps.android.math.data.UserPreferencesRepository
import hpl.apps.android.math.data.units.dimensionUnitsMap
import hpl.apps.android.math.utils.Dimension
import hpl.apps.android.math.utils.MeasurementUnit

object GetData {
    fun operators(): Operator {
        return Operator
    }

    fun functions(): MathFunction {
        return MathFunction
    }

    fun constants(): Constant {
        return Constant
    }

    fun expressions(): Expression = Expression

    fun expressionHandler(): ExpressionHandler = ExpressionHandler

    fun errorMessage(context: Context) {
        Toast.makeText(context, getString(context, CalculatorException.id), Toast.LENGTH_LONG).show()
        CalculatorException.reset()
    }

    fun getUnits():  Map<Dimension, Map<String, MeasurementUnit>>{
        return dimensionUnitsMap
    }

    const val MAX_PRECISION = UserPreferencesRepository.MAX_PRECISION
    const val MIN_PRECISION = UserPreferencesRepository.MIN_PRECISION
    const val INTERNAL_DECIMAL_SEPARATOR = '.'
    val decimalRegex = Regex("${Operator.SUB}?\\d+(\\.\\d*)?")

}