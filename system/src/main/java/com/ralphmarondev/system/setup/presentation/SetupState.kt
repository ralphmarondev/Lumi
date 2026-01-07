package com.ralphmarondev.system.setup.presentation

data class SetupState(
    val currentScreen: Int = 0,
    val completed: Boolean = false,
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)