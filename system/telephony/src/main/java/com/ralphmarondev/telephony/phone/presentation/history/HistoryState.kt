package com.ralphmarondev.telephony.phone.presentation.history

data class HistoryState(
    val navigateBack: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val callHistory: List<String> = emptyList()
)
