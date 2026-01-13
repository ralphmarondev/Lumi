package com.ralphmarondev.system.settings.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface SettingsRoute {

    @Serializable
    data object Overview : SettingsRoute

    @Serializable
    data object Account : SettingsRoute

    @Serializable
    data object Wallpapers : SettingsRoute

    @Serializable
    data object Security : SettingsRoute

    @Serializable
    data object About : SettingsRoute
}