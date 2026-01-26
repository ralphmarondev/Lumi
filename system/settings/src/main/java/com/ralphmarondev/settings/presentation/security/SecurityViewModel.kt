package com.ralphmarondev.settings.presentation.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SecurityViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SecurityState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(enableAuth = repository.isAuthEnabled())
            }
        }
    }

    fun onAction(action: SecurityAction) {
        when (action) {
            SecurityAction.NavigateBack -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            SecurityAction.ResetNavigation -> {
                _state.update {
                    it.copy(navigateBack = false)
                }
            }

            SecurityAction.ToggleEnableAuth -> {
                viewModelScope.launch {
                    val enable = _state.value.enableAuth
                    repository.setEnableAuthKey(!enable)
                    _state.update {
                        it.copy(enableAuth = !enable)
                    }
                }
            }
        }
    }
}