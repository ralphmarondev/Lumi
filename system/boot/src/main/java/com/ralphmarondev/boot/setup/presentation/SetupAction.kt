package com.ralphmarondev.boot.setup.presentation

sealed interface SetupAction {
    data object Continue : SetupAction
    data object Previous : SetupAction
    data object ResetNavigation : SetupAction
    data object ResetMessage : SetupAction
    data class SetLanguage(val value: Int) : SetupAction
    data class UsernameChange(val value: String) : SetupAction
    data class PasswordChange(val value: String) : SetupAction
    data class ConfirmPasswordChange(val value: String) : SetupAction
}