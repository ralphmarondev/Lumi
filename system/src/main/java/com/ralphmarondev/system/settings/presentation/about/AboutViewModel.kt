package com.ralphmarondev.system.settings.presentation.about

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AboutViewModel : ViewModel() {

    private val _state = MutableStateFlow(AboutState())
    val state = _state.asStateFlow()

    fun onAction(action: AboutAction) {
        when (action) {
            AboutAction.NavigateBack -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            AboutAction.ResetNavigation -> {
                _state.update {
                    it.copy(navigateBack = false)
                }
            }
        }
    }
}