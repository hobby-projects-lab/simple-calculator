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


class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val PRECISION = intPreferencesKey("precision")
        const val DEFAULT_PRECISION = 20
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
