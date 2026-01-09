package com.ralphmarondev.system.settings.presentation.overview

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OverviewViewModel : ViewModel() {

    private val _state = MutableStateFlow(OverviewState())
    val state = _state.asStateFlow()

    fun onAction(action: OverviewAction) {
        when (action) {
            OverviewAction.NavigateBack -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            OverviewAction.NavigateToAccount -> {
                _state.update {
                    it.copy(navigateToAccount = true)
                }
            }

            OverviewAction.NavigateToWallpaper -> {
                _state.update {
                    it.copy(navigateToWallpaper = true)
                }
            }

            OverviewAction.NavigateToAbout -> {
                _state.update {
                    it.copy(navigateToAbout = true)
                }
            }

            OverviewAction.NavigateToSecurity -> {
                _state.update {
                    it.copy(navigateToSecurity = true)
                }
            }

            OverviewAction.ResetNavigation -> {
                _state.update {
                    it.copy(
                        navigateToWallpaper = false,
                        navigateBack = false,
                        navigateToAccount = false,
                        navigateToSecurity = false,
                        navigateToAbout = false
                    )
                }
            }
        }
    }
}