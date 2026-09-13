package hpl.apps.android.math.utils

import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import hpl.apps.android.math.BuildConfig

interface MeasurementUnit {
    val id: Int
    val symbol: String
    val fromBase: (Double) -> Double
    val toBase: (Double) -> Double
}

interface Dimension {
    val id: Int
    val icon: ImageVector
}

const val baseUnitIndex = "BASE"
const val baseUnit2Index = "BASE2"


private const val TAG = "AppLog"
fun oops(message: String){
    if(BuildConfig.DEBUG){
        Log.d(TAG, message)
    }
    throw Exception(message)
}