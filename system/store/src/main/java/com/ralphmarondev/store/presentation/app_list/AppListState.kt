package com.ralphmarondev.store.presentation.app_list

import com.ralphmarondev.core.domain.model.LumiApp

data class AppListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val showErrorMessage: Boolean = false,
    val lumiApps: List<LumiApp> = emptyList(),
    val selectedAppId: Long = 0,
    val navigateBack: Boolean = false,
    val navigateToDetails: Boolean = false
)