package com.ralphmarondev.boot.setup.presentation

data class SetupState(
    val currentScreen: Int = 0,
    val selectedLanguage: Int = 0,
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val message: String? = null,
    val completed: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val enableContinueButton: Boolean = true
)