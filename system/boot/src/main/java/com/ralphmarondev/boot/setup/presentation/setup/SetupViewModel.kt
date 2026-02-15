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
                _state.update { state ->
                    when (state.currentScreen) {
                        state.screenCount - 1 -> {
                            state.copy(completeSetup = true)
                        }

                        state.screenCount - 2 -> {
                            val newState = validateAccount(state)
                            if (newState.isValid) {
                                newState.setupState.copy(currentScreen = state.currentScreen + 1)
                            } else {
                                newState.setupState
                            }
                        }

                        else -> {
                            state.copy(currentScreen = state.currentScreen + 1)
                        }
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
                _state.update { it.copy(installationMode = action.mode) }
            }
        }
    }

    private fun validateAccount(state: SetupState): SetupStateWithValid {
        val displayNameError =
            if (state.displayName.isBlank()) "Display name cannot be empty" else null
        val usernameError = if (state.username.isBlank()) "Username cannot be empty" else null
        val passwordError = if (state.password.isBlank()) "Password cannot be empty" else null
        val confirmPasswordError = when {
            state.confirmPassword.isBlank() -> "Confirm your password"
            state.confirmPassword != state.password -> "Passwords do not match"
            else -> null
        }

        val hasError = listOf(displayNameError, usernameError, passwordError, confirmPasswordError)
            .any { it != null }

        return SetupStateWithValid(
            setupState = state.copy(
                displayNameError = displayNameError,
                usernameError = usernameError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            ),
            isValid = !hasError
        )
    }

    private data class SetupStateWithValid(
        val setupState: SetupState,
        val isValid: Boolean
    )
}