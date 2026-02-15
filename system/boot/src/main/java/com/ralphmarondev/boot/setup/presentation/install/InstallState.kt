package com.ralphmarondev.boot.setup.presentation.install

data class InstallState(
    val screenCount: Int = 3,
    val currentScreen: Int = 0,
    val installed: Boolean = false,
    val installing: Boolean = true,
    val errorMessage: String? = null,
    val showErrorMessage: Boolean = false
)