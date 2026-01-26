package com.ralphmarondev.boot.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.boot.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay
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
                login()
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(isLoading = true, isError = false, message = null)
                }

                val result = repository.login(
                    username = _state.value.username.trim(),
                    password = _state.value.password.trim()
                )
                if (result.isSuccess) {
                    _state.update { it.copy(message = "Login successful!", isLoading = false) }
                    delay(3000)
                    _state.update { it.copy(success = true) }
                } else {
                    _state.update { it.copy(message = "Invalid credentials.", isLoading = false) }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        message = e.message
                    )
                }
            }
        }
    }
}