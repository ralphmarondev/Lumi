package com.ralphmarondev.boot.auth.presentation.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoggedIn: Boolean = false,
    val isLoggingIn: Boolean = false,
    val errorMessage: String? = null,
    val showErrorMessage: Boolean = false,
    val usernameSupportingText: String? = null,
    val passwordSupportingText: String? = null,
    val isValidUsername: Boolean = true,
    val isValidPassword: Boolean = true
)