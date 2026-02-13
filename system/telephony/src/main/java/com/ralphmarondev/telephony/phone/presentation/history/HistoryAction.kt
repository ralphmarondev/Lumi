package com.ralphmarondev.telephony.phone.presentation.history

sealed interface HistoryAction {
    data object NavigateBack : HistoryAction
    data object Refresh : HistoryAction
}