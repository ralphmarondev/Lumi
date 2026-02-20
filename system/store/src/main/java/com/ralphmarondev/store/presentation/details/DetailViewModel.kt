package com.ralphmarondev.store.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.store.domain.repository.StoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val appId: Long,
    private val repository: StoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    init {
        loadApp(appId)
    }

    fun onAction(action: DetailAction) {
        when (action) {
            DetailAction.ClearNavigation -> {
                _state.update { it.copy(navigateBack = false) }
            }

            DetailAction.NavigateBack -> {
                _state.update { it.copy(navigateBack = true) }
            }

            DetailAction.Install -> {
                installApp(id = appId)
            }

            DetailAction.UnInstall -> {
                unInstallApp(id = appId)
            }
        }
    }

    private fun loadApp(id: Long) {
        viewModelScope.launch {
            try {
                Log.d("Detail", "Loading app...")
                val app = repository.getAppById(id)
                if (app == null) {
                    _state.update {
                        it.copy(
                            errorMessage = "App not does not exist.",
                            showErrorMessage = true
                        )
                    }
                    return@launch
                }
                Log.d("Detail", "App details fetch successfully. Name: `${app.name}`")
                _state.update { it.copy(app = app) }
            } catch (e: Exception) {
                Log.e("Detail", "Error fetching. Error: ${e.localizedMessage}")
                _state.update {
                    it.copy(
                        errorMessage = e.localizedMessage ?: "Unknown error occurred.",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun installApp(id: Long) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }
                repository.installApp(id)
                _state.update {
                    it.copy(app = _state.value.app.copy(isInstalled = true))
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = e.localizedMessage ?: "Unknown error occurred.",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun unInstallApp(id: Long) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }
                repository.unInstallApp(id)
                _state.update {
                    it.copy(app = _state.value.app.copy(isInstalled = false))
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = e.localizedMessage ?: "Unknown error occurred.",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}