package com.ralphmarondev.lumi.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface SystemApp {

    @Serializable
    data object Setup : SystemApp

    @Serializable
    data object Login : SystemApp

    @Serializable
    data object Launcher : SystemApp

    @Serializable
    data object Settings : SystemApp
}