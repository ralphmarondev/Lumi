package com.ralphmarondev.settings.presentation.security

sealed interface SecurityAction {
    data object ToggleEnableAuth : SecurityAction
    data object NavigateBack : SecurityAction
    data object ResetNavigation : SecurityAction
}