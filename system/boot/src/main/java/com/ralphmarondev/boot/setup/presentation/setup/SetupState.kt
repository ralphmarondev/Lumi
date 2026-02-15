package com.ralphmarondev.boot.setup.presentation.setup

import com.ralphmarondev.core.domain.model.Language

data class SetupState(
    val screenCount: Int = 3,
    val currentScreen: Int = 0,
    val selectedLanguage: Language = Language.ENGLISH,
    val displayName: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val completeSetup: Boolean = false,
    val installLumi: InstallMode = InstallMode.InstallLumi
)

enum class InstallMode {
    TryLumi,
    InstallLumi
}