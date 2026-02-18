package com.ralphmarondev.store.presentation.app_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppListViewModel : ViewModel() {

    private val _state = MutableStateFlow(AppListState())
    val state = _state.asStateFlow()

    init {
        loadApplications()
    }

    fun onAction(action: AppListAction) {
        when (action) {
            AppListAction.Refresh -> {
                loadApplications(isRefreshing = true)
            }

            AppListAction.ClearNavigation -> {
                _state.update { it.copy(navigateBack = false) }
            }

            AppListAction.NavigateBack -> {
                _state.update { it.copy(navigateBack = true) }
            }

            is AppListAction.AppSelected -> {

            }
        }
    }

    private fun loadApplications(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        showErrorMessage = false,
                        isRefreshing = isRefreshing
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = e.localizedMessage ?: "An error occurred.",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}