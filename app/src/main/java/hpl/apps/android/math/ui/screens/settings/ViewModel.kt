package hpl.apps.android.math.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hpl.apps.android.math.CalculatorApplication
import hpl.apps.android.math.data.UserPreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val defaultDispatcher: CoroutineDispatcher
): ViewModel(){
    val precision: StateFlow<Int> =
        userPreferencesRepository.precision.map { it }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = runBlocking {
                userPreferencesRepository.precision.first()
            }
        )
    fun setPrecision(precision: Int) {
        viewModelScope.launch {
            withContext(defaultDispatcher){ userPreferencesRepository.savePrecisionPreference(precision) }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CalculatorApplication)
                SettingsViewModel(application.userPreferencesRepository, Dispatchers.Default)
            }
        }
    }
}