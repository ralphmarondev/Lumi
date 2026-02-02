package com.ralphmarondev.calendar

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object Overview : Routes
}