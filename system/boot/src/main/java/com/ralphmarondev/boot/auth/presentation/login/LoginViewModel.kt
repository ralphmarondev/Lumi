package com.ralphmarondev.boot.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.boot.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.PasswordChange -> {
                _state.update {
                    it.copy(password = action.value)
                }
            }

            is LoginAction.UsernameChange -> {
                _state.update {
                    it.copy(username = action.value)
                }
            }

            LoginAction.Login -> {
                login(
                    username = _state.value.username.trim(),
                    password = _state.value.password.trim()
                )
            }
        }
    }

    private fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoggingIn = true,
                        isLoggedIn = false,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }

                if (username.isBlank()) {
                    _state.update {
                        it.copy(
                            isValidUsername = false,
                            usernameSupportingText = "Username is empty."
                        )
                    }
                }
                if (password.isBlank()) {
                    _state.update {
                        it.copy(
                            isValidPassword = false,
                            passwordSupportingText = "Password is empty."
                        )
                    }
                }

                val result = repository.login(username, password)
                if (result.isSuccess) {
                    _state.update { it.copy(isLoggedIn = true) }
                } else {
                    _state.update {
                        it.copy(
                            isValidUsername = false,
                            isValidPassword = false,
                            usernameSupportingText = "Incorrect username or password.",
                            passwordSupportingText = "Incorrect username or password."
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = e.message ?: "Invalid credentials.",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoggingIn = false) }
            }
        }
    }
}