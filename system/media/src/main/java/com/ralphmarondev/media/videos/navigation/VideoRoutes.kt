package com.ralphmarondev.media.videos.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface VideoRoutes {

    @Serializable
    data object VideoList : VideoRoutes

    @Serializable
    data object VideoPlayer : VideoRoutes
}