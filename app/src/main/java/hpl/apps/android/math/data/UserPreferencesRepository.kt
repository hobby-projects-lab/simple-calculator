package hpl.apps.android.math.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import hpl.apps.android.math.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode


class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val DECIMAL_PLACES = intPreferencesKey("decimal places")
        private const val DEFAULT_DECIMAL_PLACES = 20

        const val MAX_DECIMAL_PLACES = 100
        const val MIN_DECIMAL_PLACES = 0

        private const val MAX_POWER_OF_TEN = 100
        private val UPPER_BOUND = BigDecimal.valueOf(1L, -MAX_POWER_OF_TEN)
        private val LOWER_BOUND = BigDecimal.valueOf(-1L, -MAX_POWER_OF_TEN)

        private const val STEP = 100
        private const val MIN_INTERNAL_PRECISION = MAX_POWER_OF_TEN+MAX_DECIMAL_PLACES
        const val MAX_INTERNAL_PRECISION = MIN_INTERNAL_PRECISION+9*STEP
        private const val MAX_INTERNAL_PRECISION_MINUS_STEP = MAX_INTERNAL_PRECISION-STEP
        fun getInternalPrecision(): Int = MIN_INTERNAL_PRECISION
        fun increaseInternalPrecision(currentInternalPrecision: Int): Int{
            return if(currentInternalPrecision <= MAX_INTERNAL_PRECISION_MINUS_STEP){
                currentInternalPrecision+STEP
            }else{MAX_INTERNAL_PRECISION}
        }

        private val smallRange = BigDecimal.valueOf(-1L, 10)..BigDecimal.valueOf(1L, 10)
        private val midRange = BigDecimal.valueOf(-1L, -10)..BigDecimal.valueOf(1L, -10)
        fun isInRange(value: BigDecimal): Boolean = value > LOWER_BOUND && value < UPPER_BOUND
        fun isInSmallRange(value: BigDecimal): Boolean = value in smallRange
        fun isInLargeRange(value: BigDecimal): Boolean = value !in midRange

        val roundingMode = RoundingMode.HALF_UP
    }

    val decimalPlaces: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                log(it.message)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[DECIMAL_PLACES] ?: DEFAULT_DECIMAL_PLACES
        }

    suspend fun saveDecimalPlaces(value: Int) {
        dataStore.edit { preferences ->
            preferences[DECIMAL_PLACES] = value
        }
    }
}
