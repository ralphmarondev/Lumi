package com.ralphmarondev.store.presentation.details

sealed interface DetailAction {
    data object NavigateBack : DetailAction
    data object ClearNavigation : DetailAction
    data object Install : DetailAction
    data object UnInstall : DetailAction
}