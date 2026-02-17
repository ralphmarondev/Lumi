package com.ralphmarondev.store

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object AppList : Routes
}