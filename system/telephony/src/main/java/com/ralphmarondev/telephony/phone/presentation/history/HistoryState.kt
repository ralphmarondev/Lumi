package com.ralphmarondev.telephony.phone.presentation.history

import com.ralphmarondev.core.domain.model.CallHistory

data class HistoryState(
    val navigateBack: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val callHistory: List<CallHistory> = emptyList(),
    val errorMessage: String? = null,
    val showErrorMessage: Boolean = false,
    val showDialPad: Boolean = false,
    val dialedNumber: String = ""
)