package com.ralphmarondev.community.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.community.auth.domain.repository.AuthRepository
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
            LoginAction.Register -> {
                _state.update { it.copy(navigateToRegister = true) }
            }

            LoginAction.NavigateBack -> {
                _state.update { it.copy(navigateBack = true) }
            }

            LoginAction.ClearNavigation -> {
                _state.update { it.copy(navigateToRegister = false, navigateBack = false) }
            }

            LoginAction.Login -> {
                login(
                    email = _state.value.email.trim(),
                    password = _state.value.password.trim()
                )
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoggingIn = true, isLoggedIn = false,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }

                val result = repository.login(email, password)
                if (result.isSuccess) {
                    _state.update { it.copy(isLoggedIn = true) }
                } else {
                    _state.update {
                        it.copy(
                            errorMessage = "Login failed.",
                            showErrorMessage = true
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = e.localizedMessage ?: "Login failed.",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoggingIn = false) }
            }
        }
    }
}