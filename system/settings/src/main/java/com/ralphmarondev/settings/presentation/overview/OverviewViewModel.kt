package com.ralphmarondev.settings.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OverviewState())
    val state = _state.asStateFlow()

    init {
        loadInformation()
    }

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

            OverviewAction.Refresh -> {
                loadInformation(isRefreshing = true)
            }

            OverviewAction.LoadInformation -> {
                loadInformation()
            }
        }
    }

    private fun loadInformation(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isRefreshing = isRefreshing,
                    errorMessage = null,
                    showErrorMessage = false
                )
            }

            val result = repository.getUserInformation()
            if (isRefreshing) {
                delay(1000)
            }

            when (result) {
                is Result.Success -> {
                    _state.update {
                        it.copy(user = result.data)
                    }
                }

                is Result.Error -> {
                    _state.update {
                        it.copy(errorMessage = it.errorMessage, showErrorMessage = true)
                    }
                }
            }

            _state.update { it.copy(isLoading = false, isRefreshing = false) }
        }
    }
}