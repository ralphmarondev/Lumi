package com.ralphmarondev.community.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoggingIn: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val showErrorMessage: Boolean = false,
    val navigateBack: Boolean = false,
    val navigateToRegister: Boolean = false
)