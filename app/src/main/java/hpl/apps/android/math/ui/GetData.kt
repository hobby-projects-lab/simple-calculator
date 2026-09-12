package hpl.apps.android.math.ui

import hpl.apps.android.math.data.Constant
import hpl.apps.android.math.data.Expression
import hpl.apps.android.math.data.ExpressionHandler
import hpl.apps.android.math.data.MathFunction
import hpl.apps.android.math.data.Operator
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

    fun getUnits():  Map<Dimension, Map<String, MeasurementUnit>>{
        return dimensionUnitsMap
    }

    const val INTERNAL_DECIMAL_SEPARATOR = '.'

}