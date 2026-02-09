package com.ralphmarondev.telephony.contacts.di

import com.ralphmarondev.telephony.contacts.data.repository.ContactsRepositoryImpl
import com.ralphmarondev.telephony.contacts.domain.repository.ContactsRepository
import com.ralphmarondev.telephony.contacts.presentation.contact_list.ContactListViewModel
import com.ralphmarondev.telephony.contacts.presentation.new_contact.NewContactViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val contactsModule = module {
    single<ContactsRepository> { ContactsRepositoryImpl(get()) }
    viewModelOf(::ContactListViewModel)
    viewModelOf(::NewContactViewModel)
}