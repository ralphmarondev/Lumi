package com.ralphmarondev.launcher.presentation

sealed interface LauncherAction {
    data object NavigateToSettings : LauncherAction
    data object NavigateToNotes : LauncherAction
    data object NavigateToClock : LauncherAction
    data object NavigateToWeather : LauncherAction
    data object NavigateToCalendar : LauncherAction
    data object NavigateToCamera : LauncherAction
    data object NavigateToPhotos : LauncherAction
    data object NavigateToVideos : LauncherAction
    data object NavigateToContacts : LauncherAction
    data object ResetNavigation : LauncherAction
}