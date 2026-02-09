package com.ralphmarondev.core.data.local.database.mapper

import com.ralphmarondev.core.data.local.database.entities.ContactEntity
import com.ralphmarondev.core.domain.model.Contact

fun Contact.toEntity(): ContactEntity {
    return ContactEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        contactImagePath = contactImagePath
    )
}

fun ContactEntity.toDomain(): Contact {
    return Contact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        contactImagePath = contactImagePath
    )
}