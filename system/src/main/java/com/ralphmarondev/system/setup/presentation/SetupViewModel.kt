package com.ralphmarondev.system.setup.presentation

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
                    val currentScreen = _state.value.currentScreen + 1

                    if (currentScreen > 4) {
                        it.copy(completed = true)
                    } else {
                        it.copy(currentScreen = currentScreen)
                    }
                }
            }

            SetupAction.Previous -> {
                _state.update {
                    val currentScreen = _state.value.currentScreen - 1
                    it.copy(currentScreen = currentScreen)
                }
            }

            SetupAction.Reset -> {
                _state.update {
                    it.copy(completed = false)
                }
            }

            is SetupAction.ConfirmPasswordChange -> {
                _state.update {
                    it.copy(confirmPassword = action.value)
                }
            }

            is SetupAction.PasswordChange -> {
                _state.update {
                    it.copy(password = action.value)
                }
            }

            is SetupAction.UsernameChange -> {
                _state.update {
                    it.copy(username = action.value)
                }
            }
        }
    }
}