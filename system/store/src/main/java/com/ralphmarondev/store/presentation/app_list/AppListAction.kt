package com.ralphmarondev.store.presentation.app_list

sealed interface AppListAction {
    data object Refresh : AppListAction
    data object NavigateBack : AppListAction
    data object ClearNavigation : AppListAction
    data class AppSelected(val tag: String) : AppListAction
}