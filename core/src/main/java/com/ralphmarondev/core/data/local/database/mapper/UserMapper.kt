package com.ralphmarondev.core.data.local.database.mapper

import com.ralphmarondev.core.data.local.database.entities.UserEntity
import com.ralphmarondev.core.domain.model.User

fun User.toEntity(): UserEntity {
    val now = System.currentTimeMillis()

    return UserEntity(
        username = username,
        password = password,
        displayName = displayName,
        email = email,
        phoneNumber = phoneNumber,
        gender = gender,
        birthday = birthday,
        profileImagePath = profileImagePath,
        description = description,
        role = role,
        language = language,
        street = street,
        city = city,
        province = province,
        postalCode = postalCode,
        country = country,
        active = true,
        createdAt = now,
        updatedAt = now
    )
}

fun User.toEntity(existing: UserEntity): UserEntity {
    return existing.copy(
        username = username,
        password = password,
        displayName = displayName,
        email = email,
        phoneNumber = phoneNumber,
        gender = gender,
        birthday = birthday,
        profileImagePath = profileImagePath,
        description = description,
        role = role,
        language = language,
        street = street,
        city = city,
        province = province,
        postalCode = postalCode,
        country = country,
        updatedAt = System.currentTimeMillis()
    )
}

fun UserEntity.toDomain(): User {
    return User(
        username = username,
        password = password,
        displayName = displayName,
        email = email,
        phoneNumber = phoneNumber,
        gender = gender,
        birthday = birthday,
        profileImagePath = profileImagePath,
        description = description,
        role = role,
        language = language,
        street = street,
        city = city,
        province = province,
        postalCode = postalCode,
        country = country
    )
}