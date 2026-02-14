package com.ralphmarondev.boot.setup

import kotlinx.serialization.Serializable

@Serializable
sealed interface SetupRoute {

    @Serializable
    data object Setup : SetupRoute

    @Serializable
    data object Installing : SetupRoute

    @Serializable
    data object Completed : SetupRoute
}