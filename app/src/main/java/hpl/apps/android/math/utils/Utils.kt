package hpl.apps.android.math.utils

import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import hpl.apps.android.math.BuildConfig
import java.math.BigDecimal

interface MeasurementUnit {
    val id: Int
    val symbol: String
    val fromBase: (BigDecimal) -> BigDecimal
    val toBase: (BigDecimal) -> BigDecimal
}

interface Dimension {
    val id: Int
    val icon: ImageVector
}

const val baseUnitIndex = "BASE"
const val baseUnit2Index = "BASE2"


private const val TAG = "AppLog"
fun oops(message: String?){
    if(BuildConfig.DEBUG){
        Log.d(TAG, message?: "Oops")
    }
    throw Exception(message)
}
fun log(message: String?){
    if(BuildConfig.DEBUG){
        Log.d(TAG, message?: "Oops")
    }
}