package com.ralphmarondev.system.settings.presentation.overview

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OverviewViewModel : ViewModel() {

    private val _state = MutableStateFlow(OverviewState())
    val state = _state.asStateFlow()

    fun onAction(action: OverviewAction) {

    }
}