package com.ralphmarondev.weather

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object Home : Routes
}