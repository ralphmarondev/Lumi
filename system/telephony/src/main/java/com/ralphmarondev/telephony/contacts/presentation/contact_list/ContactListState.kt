package com.ralphmarondev.telephony.contacts.presentation.contact_list

import com.ralphmarondev.core.domain.model.Contact

data class ContactListState(
    val contacts: List<Contact> = emptyList(),
    val navigateBack: Boolean = false,
    val createNewContact: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)