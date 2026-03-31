package com.openkin.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.ISettingsInteractor
import com.openkin.presentation.ui.settings.model.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsInteractor: ISettingsInteractor,
) : ViewModel() {

    private val _viewState = MutableStateFlow<AppTheme>(AppTheme.SYSTEM)
    val viewState: StateFlow<AppTheme> = _viewState.asStateFlow()

    fun onThemeChanged(theme: AppTheme) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsInteractor.saveSelectedTheme(theme.name)
        }
    }

    fun loadSelectedTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsInteractor.getStoredTheme().collect { themeName ->
                _viewState.value =
                    AppTheme.entries.firstOrNull { it.name == themeName } ?: AppTheme.SYSTEM
            }
        }
    }
}
