package com.ralphmarondev.boot.setup.presentation.setup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SetupViewModel : ViewModel() {
    private val _state = MutableStateFlow(SetupState())
    val state = _state.asStateFlow()

    fun onAction(action: SetupAction) {
        when (action) {
            SetupAction.Continue -> {
                _state.update {
                    if (it.currentScreen == it.screenCount - 1) {
                        it.copy(completeSetup = true)
                    } else {
                        it.copy(currentScreen = it.currentScreen + 1)
                    }
                }
            }

            SetupAction.Previous -> {
                _state.update {
                    it.copy(
                        currentScreen = (it.currentScreen - 1).coerceAtLeast(0)
                    )
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