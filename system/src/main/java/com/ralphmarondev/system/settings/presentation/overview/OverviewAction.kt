package com.ralphmarondev.system.settings.presentation.overview

sealed interface OverviewAction {
    data object NavigateToAccount : OverviewAction
    data object NavigateToWallpaper : OverviewAction
    data object ResetNavigation : OverviewAction
}