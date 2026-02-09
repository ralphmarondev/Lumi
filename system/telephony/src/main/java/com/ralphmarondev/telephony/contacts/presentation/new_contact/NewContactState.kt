package com.ralphmarondev.telephony.contacts.presentation.new_contact

data class NewContactState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val contactImagePath: String? = null,
    val navigateBack: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)