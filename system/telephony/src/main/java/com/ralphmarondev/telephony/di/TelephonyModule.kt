package com.ralphmarondev.telephony.di

import com.ralphmarondev.telephony.contacts.di.contactsModule
import org.koin.dsl.module

val telephonyModule = module {
    includes(contactsModule)
}