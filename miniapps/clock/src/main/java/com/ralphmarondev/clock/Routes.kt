package com.ralphmarondev.clock

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object Alarms : Routes
}