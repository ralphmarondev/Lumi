package com.ralphmarondev.boot.auth.presentation.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String? = null
)
