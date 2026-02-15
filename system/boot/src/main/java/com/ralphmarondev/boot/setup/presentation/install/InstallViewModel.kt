package com.ralphmarondev.boot.setup.presentation.install

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InstallViewModel : ViewModel() {

    private val _state = MutableStateFlow(InstallState())
    val state = _state.asStateFlow()

    fun onAction(action: InstallAction) {

    }
}