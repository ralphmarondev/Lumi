package com.ralphmarondev.system.auth.presentation.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val success: Boolean = false
)
