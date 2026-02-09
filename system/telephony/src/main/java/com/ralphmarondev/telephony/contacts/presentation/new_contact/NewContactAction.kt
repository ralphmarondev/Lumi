package com.ralphmarondev.telephony.contacts.presentation.new_contact

sealed interface NewContactAction {
    data object Save : NewContactAction
    data object Back : NewContactAction
    data object ResetNavigation : NewContactAction

    data class FirstNameChange(val firstName: String) : NewContactAction
    data class LastNameChange(val lastName: String) : NewContactAction
    data class PhoneNumberChange(val phoneNumber: String) : NewContactAction
}