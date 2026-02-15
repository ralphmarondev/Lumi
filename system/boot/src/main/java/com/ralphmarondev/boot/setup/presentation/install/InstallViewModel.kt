package com.ralphmarondev.boot.setup.presentation.install

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.boot.setup.domain.model.SetupResult
import com.ralphmarondev.boot.setup.domain.repository.SetupRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InstallViewModel(
    private val setupResult: SetupResult,
    private val repository: SetupRepository
) : ViewModel() {

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

            InstallAction.Continue -> {
                moveNext()
            }

            InstallAction.Previous -> {
                movePrevious()
            }
        }
    }

    private fun moveNext() {
        _state.update { state ->
            val next = if (state.currentScreen == state.screenCount - 1) 0
            else state.currentScreen + 1
            state.copy(currentScreen = next)
        }
    }

    private fun movePrevious() {
        _state.update { state ->
            state.copy(currentScreen = (state.currentScreen - 1).coerceAtLeast(0))
        }
    }

    private fun installLumi() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    installing = true,
                    installed = false,
                    errorMessage = null,
                    showErrorMessage = false,
                    currentScreen = 0
                )
            }

            val autoScrollJob = launch {
                while (_state.value.installing) {
                    delay(1000)
                    _state.update { state ->
                        val next = (state.currentScreen + 1) % state.screenCount
                        state.copy(currentScreen = next)
                    }
                }
            }

            try {
                Log.d("Install", "Installing Lumi...")
                Log.d("Install", "Language: ${setupResult.selectedLanguage}")
                Log.d("Install", "DisplayName: ${setupResult.displayName}")
                Log.d("Install", "Username: ${setupResult.username}")
                Log.d("Install", "Password: ${setupResult.password}")
                delay(10000)
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
                autoScrollJob.cancel()
            }
        }
    }
}