package com.ralphmarondev.boot.setup.presentation.install

data class InstallState(
    val installed: Boolean = false,
    val installing: Boolean = true,
    val errorMessage: String? = null,
    val showErrorMessage: Boolean = false
)