package com.ralphmarondev.media.photos.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface PhotoRoutes {

    @Serializable
    data object Gallery : PhotoRoutes
}