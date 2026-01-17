package com.ralphmarondev.weather.presentation.home

sealed interface HomeAction {
    data object Refresh : HomeAction
    data object NavigateBack : HomeAction
    data object ResetNavigation : HomeAction
}