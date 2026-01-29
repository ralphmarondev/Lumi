package com.ralphmarondev.media.photos.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface PhotoRoutes {

    @Serializable
    data object Gallery : PhotoRoutes

    @Serializable
    data class Details(val imagePath: String) : PhotoRoutes
}