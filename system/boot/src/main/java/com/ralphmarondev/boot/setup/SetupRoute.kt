package com.ralphmarondev.boot.setup

import kotlinx.serialization.Serializable

@Serializable
sealed interface SetupRoute {

    @Serializable
    data object Setup : SetupRoute

    @Serializable
    data class Installing(
        val selectedLanguage: String,
        val displayName: String,
        val username: String,
        val password: String
    ) : SetupRoute

    @Serializable
    data object Completed : SetupRoute
}