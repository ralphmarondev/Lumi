package com.ralphmarondev.telephony.contacts

import kotlinx.serialization.Serializable

@Serializable
sealed interface ContactsRoute {

    @Serializable
    data object ContactList : ContactsRoute
}