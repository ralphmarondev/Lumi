package com.ralphmarondev.system.settings.presentation.about

sealed interface AboutAction {
    data object NavigateBack : AboutAction
    data object ResetNavigation : AboutAction
}