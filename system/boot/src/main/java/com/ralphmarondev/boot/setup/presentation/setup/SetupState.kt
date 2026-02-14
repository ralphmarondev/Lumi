package com.ralphmarondev.boot.setup.presentation.setup

data class SetupState(
    val currentScreen: Int = 0,
    val selectedLanguage: Int = 0,
    val displayName: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val message: String? = null,
    val completed: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val enableContinueButton: Boolean = true,
    val currentPage: Page = Page.Setup
)

enum class Page {
    Setup,
    Finalizing,
    AllSet
}