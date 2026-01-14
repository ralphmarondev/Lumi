package com.ralphmarondev.lumi.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object Setup : Routes

    @Serializable
    data object Login : Routes

    @Serializable
    data object Launcher : Routes

    @Serializable
    data object Settings : Routes
}