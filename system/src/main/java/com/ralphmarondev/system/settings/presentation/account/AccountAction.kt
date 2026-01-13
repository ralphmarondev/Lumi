package com.ralphmarondev.system.settings.presentation.account

sealed interface AccountAction {
    data object Refresh : AccountAction
    data object NavigateBack : AccountAction
    data object ResetNavigation : AccountAction
}