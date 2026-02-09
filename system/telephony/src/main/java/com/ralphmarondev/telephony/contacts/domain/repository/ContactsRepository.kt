package com.ralphmarondev.telephony.contacts.domain.repository

import com.ralphmarondev.core.domain.model.Contact

interface ContactsRepository {
    suspend fun getAllContact(): List<Contact>
}