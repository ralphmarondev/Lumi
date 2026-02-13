package com.ralphmarondev.telephony.di

import com.ralphmarondev.telephony.contacts.di.contactsModule
import com.ralphmarondev.telephony.phone.di.phoneModule
import org.koin.dsl.module

val telephonyModule = module {
    includes(contactsModule)
    includes(phoneModule)
}