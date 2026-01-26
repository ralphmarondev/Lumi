package com.ralphmarondev.settings.presentation.overview

sealed interface OverviewAction {
    data object NavigateToAccount : OverviewAction
    data object NavigateToWallpaper : OverviewAction
    data object NavigateToSecurity : OverviewAction
    data object NavigateToAbout : OverviewAction
    data object NavigateBack : OverviewAction
    data object ResetNavigation : OverviewAction
    data object Refresh : OverviewAction
}