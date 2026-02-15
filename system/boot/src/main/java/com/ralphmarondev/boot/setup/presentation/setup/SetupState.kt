package com.ralphmarondev.boot.setup.presentation.setup

import com.ralphmarondev.core.domain.model.Language

data class SetupState(
    val screenCount: Int = 4,
    val currentScreen: Int = 0,
    val selectedLanguage: Language = Language.ENGLISH,
    val displayName: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val completeSetup: Boolean = false,
    val installationMode: InstallMode = InstallMode.InstallLumi,
    val displayNameError: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)

enum class InstallMode {
    TryLumi,
    InstallLumi
}