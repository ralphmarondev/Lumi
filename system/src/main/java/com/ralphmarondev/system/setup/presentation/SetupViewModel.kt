package com.ralphmarondev.system.setup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetupViewModel : ViewModel() {
    private val _state = MutableStateFlow(SetupState())
    val state = _state.asStateFlow()

    fun onAction(action: SetupAction) {
        when (action) {
            SetupAction.Continue -> {
                _state.update { current ->
                    val nextScreen = current.currentScreen + 1

                    if (nextScreen == 4) {
                        val isValid = current.password == current.confirmPassword &&
                                current.confirmPassword.isNotBlank() &&
                                current.username.isNotBlank()

                        if (!isValid) return@update current
                    }

                    if (nextScreen > 4) {
                        setup()
                        current
                    } else {
                        current.copy(
                            currentScreen = nextScreen,
                            enableContinueButton = nextScreen != 3
                        )
                    }
                }
            }

            SetupAction.Previous -> {
                _state.update {
                    val currentScreen = _state.value.currentScreen - 1
                    it.copy(currentScreen = currentScreen)
                }
            }

            SetupAction.ResetNavigation -> {
                _state.update {
                    it.copy(completed = false)
                }
            }

            is SetupAction.ConfirmPasswordChange -> {
                _state.update { current ->
                    val isValid = _state.value.password == action.value
                            && action.value.isNotBlank()
                            && _state.value.username.isNotBlank()

                    current.copy(
                        confirmPassword = action.value,
                        enableContinueButton = isValid
                    )
                }
            }

            is SetupAction.PasswordChange -> {
                _state.update {
                    it.copy(password = action.value, enableContinueButton = false)
                }
            }

            is SetupAction.UsernameChange -> {
                _state.update {
                    it.copy(username = action.value, enableContinueButton = false)
                }
            }

            is SetupAction.SetLanguage -> {
                _state.update {
                    it.copy(selectedLanguage = action.value)
                }
            }

            SetupAction.ResetMessage -> {
                _state.update {
                    it.copy(message = null)
                }
            }
        }
    }

    private fun setup() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, enableContinueButton = false) }

                delay(3000)

                _state.update {
                    it.copy(
                        isLoading = false,
                        enableContinueButton = true,
                        completed = true,
                        message = "Setup Completed!"
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        enableContinueButton = true,
                        message = e.message
                    )
                }
            }
        }
    }
}