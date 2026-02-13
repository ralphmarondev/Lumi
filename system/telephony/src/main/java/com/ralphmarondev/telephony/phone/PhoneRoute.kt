package com.ralphmarondev.telephony.phone

import kotlinx.serialization.Serializable

@Serializable
sealed interface PhoneRoute {

    @Serializable
    data object History : PhoneRoute
}