package com.ralphmarondev.boot.setup.presentation.setup

import androidx.lifecycle.ViewModel
import com.ralphmarondev.boot.setup.domain.repository.SetupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SetupViewModel(
    private val repository: SetupRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SetupState())
    val state = _state.asStateFlow()

    fun onAction(action: SetupAction) {
        when (action) {
            SetupAction.Continue -> {
                _state.update {
                    val currentScreen = _state.value.currentScreen
                    val screenCount = _state.value.screenCount
                    if (currentScreen < screenCount - 1) {
                        it.copy(currentScreen = currentScreen + 1)
                    } else {
                        it
                    }
                }
                _state.update {
                    val newCurrentScreen = _state.value.currentScreen
                    if (newCurrentScreen >= _state.value.screenCount) {
                        it.copy(completeSetup = true)
                    } else {
                        it
                    }
                }
            }

            SetupAction.Previous -> {
                _state.update {
                    val currentScreen = _state.value.currentScreen
                    if (currentScreen > 1) {
                        it.copy(currentScreen = currentScreen - 1)
                    } else {
                        it
                    }
                }
            }


            is SetupAction.ConfirmPasswordChange -> {
                _state.update { it.copy(confirmPassword = action.confirmPassword) }
            }

            is SetupAction.PasswordChange -> {
                _state.update { it.copy(password = action.password) }
            }

            is SetupAction.UsernameChange -> {
                _state.update { it.copy(username = action.username) }
            }

            is SetupAction.SetLanguage -> {
                _state.update { it.copy(selectedLanguage = action.language) }
            }

            is SetupAction.DisplayNameChange -> {
                _state.update { it.copy(displayName = action.displayName) }
            }

            is SetupAction.SetInstallMode -> {
                _state.update { it.copy(installLumi = action.mode) }
            }
        }
    }
}