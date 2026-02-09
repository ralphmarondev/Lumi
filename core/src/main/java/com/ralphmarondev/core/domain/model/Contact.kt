package com.ralphmarondev.core.domain.model

data class Contact(
    val id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val contactImagePath: String? = null
)