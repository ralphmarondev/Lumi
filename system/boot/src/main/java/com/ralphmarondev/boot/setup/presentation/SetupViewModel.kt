package com.ralphmarondev.boot.setup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.boot.setup.domain.repository.SetupRepository
import com.ralphmarondev.core.domain.model.Language
import com.ralphmarondev.core.domain.model.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetupViewModel(
    private val repository: SetupRepository
) : ViewModel() {
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

                val selectedLanguage = when (_state.value.selectedLanguage) {
                    0 -> Language.ENGLISH
                    1 -> Language.FILIPINO
                    else -> Language.ENGLISH
                }

                val validationResult = validateInput(
                    username = _state.value.username.trim(),
                    password = _state.value.password.trim(),
                    confirmPassword = _state.value.confirmPassword.trim()
                )

                if (validationResult is Result.Error) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            enableContinueButton = true,
                            message = validationResult.message
                        )
                    }
                    return@launch
                }

                val result = repository.setup(
                    language = selectedLanguage,
                    username = _state.value.username.trim(),
                    password = _state.value.password.trim()
                )
                delay(3000)
                _state.update {
                    when (result) {
                        is Result.Success -> it.copy(
                            isLoading = false,
                            enableContinueButton = true,
                            completed = true,
                            message = "Setup Completed!"
                        )

                        is Result.Error -> it.copy(
                            isLoading = false,
                            isError = true,
                            enableContinueButton = true,
                            message = result.message
                        )

                        else -> it
                    }
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

    private suspend fun validateInput(
        username: String,
        password: String,
        confirmPassword: String
    ): Result<Unit> {
        if (username.isBlank()) return Result.Error("Username cannot be empty")
        if (password.isBlank()) return Result.Error("Password cannot be empty")
        if (password != confirmPassword) return Result.Error("Passwords do not match")

        val existingUser = repository.getUserByUsername(username)
        if (existingUser != null) return Result.Error("Username already exists")

        return Result.Success(Unit)
    }
}