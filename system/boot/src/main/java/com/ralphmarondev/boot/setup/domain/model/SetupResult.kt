package com.ralphmarondev.boot.setup.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SetupResult(
    val selectedLanguage: String,
    val displayName: String,
    val username: String,
    val password: String
)