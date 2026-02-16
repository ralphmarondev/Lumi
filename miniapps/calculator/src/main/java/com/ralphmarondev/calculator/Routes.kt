package com.ralphmarondev.calculator

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object Calculate : Routes
}