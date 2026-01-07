package com.ralphmarondev.system.launcher.presentation

sealed interface LauncherAction {
    data object NavigateToSettings : LauncherAction
    data object NavigateToNotes : LauncherAction
    data object NavigateToClock : LauncherAction
    data object NavigateToWeather : LauncherAction
    data object ResetNavigation : LauncherAction
}