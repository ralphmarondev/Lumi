package com.ralphmarondev.telephony.contacts.presentation.contact_list

sealed interface ContactListAction {
    data object Refresh : ContactListAction
    data object Create : ContactListAction
    data object Back : ContactListAction
    data object ResetNavigation : ContactListAction
}