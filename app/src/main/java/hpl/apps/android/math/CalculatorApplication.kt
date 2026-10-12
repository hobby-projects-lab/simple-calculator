package hpl.apps.android.math


import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import hpl.apps.android.math.data.UserPreferencesRepository

private const val CALCULATOR_PREFERENCE_NAME = "calculator_preference"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = CALCULATOR_PREFERENCE_NAME
)

class CalculatorApplication: Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}