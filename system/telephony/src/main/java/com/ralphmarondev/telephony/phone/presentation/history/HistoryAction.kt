package com.ralphmarondev.telephony.phone.presentation.history

sealed interface HistoryAction {
    data object NavigateBack : HistoryAction
    data object Refresh : HistoryAction

    data class DialKey(val key: String) : HistoryAction
    data object DeleteKey : HistoryAction
    data object ShowDialPad : HistoryAction
    data object HideDialPad : HistoryAction
    data object CallNumber : HistoryAction
}