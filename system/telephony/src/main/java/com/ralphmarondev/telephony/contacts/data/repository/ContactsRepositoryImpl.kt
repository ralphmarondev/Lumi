package com.ralphmarondev.telephony.contacts.data.repository

import com.ralphmarondev.core.data.local.database.dao.ContactDao
import com.ralphmarondev.core.data.local.database.mapper.toDomain
import com.ralphmarondev.core.data.local.database.mapper.toEntity
import com.ralphmarondev.core.domain.model.Contact
import com.ralphmarondev.telephony.contacts.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val contactDao: ContactDao
) : ContactsRepository {
    override suspend fun getAllContact(): List<Contact> {
        return contactDao.getAll().map { it.toDomain() }
    }

    override suspend fun create(contact: Contact) {
        contactDao.create(contactEntity = contact.toEntity())
    }
}