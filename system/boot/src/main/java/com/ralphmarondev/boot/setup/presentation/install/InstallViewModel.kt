package com.ralphmarondev.boot.setup.presentation.install

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InstallViewModel : ViewModel() {

    private val _state = MutableStateFlow(InstallState())
    val state = _state.asStateFlow()

    init {
        installLumi()
    }

    fun onAction(action: InstallAction) {
        when (action) {
            InstallAction.Retry -> {
                installLumi()
            }
        }
    }

    private fun installLumi() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    installing = true,
                    installed = false,
                    errorMessage = null,
                    showErrorMessage = false
                )
            }
            try {
                Log.d("Install", "Installing Lumi...")
                delay(2000)
                Log.d("Install", "Done...")
                _state.update { it.copy(installed = true) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Error: ${e.message ?: "Unknown error"}",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(installing = false) }
            }
        }
    }
}