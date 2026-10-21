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
        private val PRECISION = intPreferencesKey("precision")
        private const val DEFAULT_PRECISION = 20
        const val MAX_PRECISION = 100
        const val MIN_PRECISION = 0

        val roundingMode = RoundingMode.HALF_UP

        private const val STEP = 100
        private const val MIN_INTERNAL_PRECISION = MAX_PRECISION+STEP
        const val MAX_INTERNAL_PRECISION = MIN_INTERNAL_PRECISION+9*STEP
        private const val MAX_INTERNAL_PRECISION_MINUS_STEP = MAX_INTERNAL_PRECISION-STEP

        private val MAX = BigDecimal.valueOf(1L, -STEP)
        private val MIN = BigDecimal.valueOf(-1L, -STEP)

        fun isInRange(value: BigDecimal): Boolean = value > MIN && value < MAX
        val MAX_FACTORIAL_INPUT: BigDecimal = BigDecimal.valueOf(69L, 0)

        fun getInternalPrecision(): Int = MIN_INTERNAL_PRECISION
        fun increaseInternalPrecision(currentInternalPrecision: Int): Int{
            return if(currentInternalPrecision <= MAX_INTERNAL_PRECISION_MINUS_STEP){
                currentInternalPrecision+STEP
            }else{MAX_INTERNAL_PRECISION}
        }

    }

    val precision: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                log(it.message)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[PRECISION] ?: DEFAULT_PRECISION
        }

    suspend fun savePrecisionPreference(precision: Int) {
        dataStore.edit { preferences ->
            preferences[PRECISION] = precision
        }
    }
}
