package com.ralphmarondev.telephony.phone.presentation.history

import android.app.Application
import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.domain.model.CallHistory
import com.ralphmarondev.core.domain.model.CallType
import com.ralphmarondev.telephony.phone.domain.repository.PhoneRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: PhoneRepository,
    application: Application
) : AndroidViewModel(application) {

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

            HistoryAction.CallNumber -> {
                callNumber(number = _state.value.dialedNumber.trim())
            }
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
                val callHistory = repository.getCallHistory()
                if (isRefreshing) {
                    delay(1000)
                }
                _state.update { it.copy(callHistory = callHistory) }
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

    private fun callNumber(number: String) {
        try {
            if (number.isBlank()) return

            _state.update {
                it.copy(errorMessage = null, showErrorMessage = false)
            }

            val intent = Intent(Intent.ACTION_CALL).apply {
                data = "tel:$number".toUri()
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            getApplication<Application>().startActivity(intent)

            val call = CallHistory(
                owner = "system",
                name = null,
                number = number,
                type = CallType.Outgoing.name,
                date = System.currentTimeMillis(),
                duration = 0
            )

            viewModelScope.launch {
                repository.saveCall(call)
                loadCallHistory()
            }
            _state.update { it.copy(dialedNumber = "", showDialPad = false) }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    errorMessage = "Failed to initiate call. Error: ${e.message}",
                    showErrorMessage = true
                )
            }
        }
    }
}