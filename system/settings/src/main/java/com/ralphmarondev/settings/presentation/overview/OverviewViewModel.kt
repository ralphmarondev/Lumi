package com.ralphmarondev.settings.presentation.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.settings.domain.repository.SettingsRepository
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
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, isRefreshing = true) }
                    val result = repository.getUserInformation()
                    Log.d("Settings", "Result: $result")

                    when (result) {
                        is Result.Success -> {
                            _state.update {
                                it.copy(
                                    user = result.data,
                                    isLoading = false,
                                    isRefreshing = false
                                )
                            }
                        }

                        is Result.Error -> {
                            _state.update {
                                it.copy(
                                    errorMessage = it.errorMessage,
                                    isLoading = false,
                                    isRefreshing = false
                                )
                            }
                        }

                        Result.Loading -> Unit
                    }
                }
            }
        }
    }

    private fun loadInformation() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = repository.getUserInformation()
            Log.d("Settings", "Result: $result")

            when (result) {
                is Result.Success -> {
                    Log.d("Settings", "Inside Result.Success (result.data) => ${result.data}")
                    _state.update {
                        it.copy(
                            user = result.data,
                            isLoading = false
                        )
                    }
                }

                is Result.Error -> {
                    _state.update {
                        it.copy(errorMessage = it.errorMessage, isLoading = false)
                    }
                }

                Result.Loading -> Unit
            }

            Log.d("Settings", "After when. ${_state.value.user.profileImagePath}")
        }
    }
}