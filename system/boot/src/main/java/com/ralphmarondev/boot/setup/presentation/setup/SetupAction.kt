package com.ralphmarondev.boot.setup.presentation.setup

import com.ralphmarondev.core.domain.model.Language

sealed interface SetupAction {
    data object Continue : SetupAction
    data object Previous : SetupAction
    data class SetLanguage(val language: Language) : SetupAction
    data class DisplayNameChange(val displayName: String) : SetupAction
    data class UsernameChange(val username: String) : SetupAction
    data class PasswordChange(val password: String) : SetupAction
    data class ConfirmPasswordChange(val confirmPassword: String) : SetupAction
    data class SetInstallMode(val mode: InstallMode) : SetupAction
}