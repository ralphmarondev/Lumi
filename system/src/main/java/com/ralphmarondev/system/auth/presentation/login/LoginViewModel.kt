package com.ralphmarondev.system.auth.presentation.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

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
                _state.update {
                    it.copy(success = true)
                }
            }
        }
    }
}