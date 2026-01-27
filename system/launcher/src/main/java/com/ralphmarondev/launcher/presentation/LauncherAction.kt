package com.ralphmarondev.launcher.presentation

sealed interface LauncherAction {
    data class OnAppClick(val tag: String) : LauncherAction
    data object ResetNavigation : LauncherAction
}