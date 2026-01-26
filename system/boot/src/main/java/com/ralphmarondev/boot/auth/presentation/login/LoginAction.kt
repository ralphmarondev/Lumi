package com.ralphmarondev.boot.auth.presentation.login

sealed interface LoginAction {
    data class UsernameChange(val value: String) : LoginAction
    data class PasswordChange(val value: String) : LoginAction
    data object Login : LoginAction
}