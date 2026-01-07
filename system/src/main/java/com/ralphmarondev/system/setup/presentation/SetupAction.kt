package com.ralphmarondev.system.setup.presentation

sealed interface SetupAction {
    data object Continue : SetupAction
    data object Previous : SetupAction
    data object Reset : SetupAction

    data class UsernameChange(val value: String) : SetupAction
    data class PasswordChange(val value: String) : SetupAction
    data class ConfirmPasswordChange(val value: String) : SetupAction
}