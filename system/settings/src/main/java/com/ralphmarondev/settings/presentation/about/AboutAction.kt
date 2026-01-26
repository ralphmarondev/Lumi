package com.ralphmarondev.settings.presentation.about

sealed interface AboutAction {
    data object NavigateBack : AboutAction
    data object ResetNavigation : AboutAction
}