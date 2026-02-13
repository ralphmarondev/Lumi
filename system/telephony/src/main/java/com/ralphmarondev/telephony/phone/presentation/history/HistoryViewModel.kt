package com.ralphmarondev.telephony.phone.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()

    init {
        loadCallHistory()
    }

    fun onAction(action: HistoryAction) {
        when (action) {
            HistoryAction.NavigateBack -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            HistoryAction.Refresh -> {
                loadCallHistory(isRefreshing = true)
            }

            is HistoryAction.DialKey -> {
                _state.update {
                    it.copy(dialedNumber = it.dialedNumber + action.key)
                }
            }

            HistoryAction.DeleteKey -> {
                _state.update {
                    val currentNumber = it.dialedNumber
                    if (currentNumber.isNotEmpty()) {
                        it.copy(dialedNumber = currentNumber.dropLast(1))
                    } else {
                        it
                    }
                }
            }

            HistoryAction.HideDialPad -> {
                _state.update { it.copy(showDialPad = false) }
            }

            HistoryAction.ShowDialPad -> {
                _state.update { it.copy(showDialPad = true) }
            }

            HistoryAction.CallNumber -> {}
        }
    }

    private fun loadCallHistory(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true, isRefreshing = isRefreshing,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }
                // call repository here
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Failed loading call history. Error: ${e.message}",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false, isRefreshing = false) }
            }
        }
    }
}