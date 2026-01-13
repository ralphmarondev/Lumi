package com.ralphmarondev.system.settings.presentation.security

data class SecurityState(
    val enableAuth: Boolean = true,
    val navigateBack: Boolean = false,
    val message: String? = null
)