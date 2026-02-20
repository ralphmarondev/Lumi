package com.ralphmarondev.store.presentation.details

import com.ralphmarondev.core.domain.model.LumiApp

data class DetailState(
    val app: LumiApp = LumiApp(),
    val navigateBack: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showErrorMessage: Boolean = false
)