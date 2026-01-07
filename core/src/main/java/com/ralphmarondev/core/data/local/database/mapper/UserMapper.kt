package com.ralphmarondev.core.data.local.database.mapper

import com.ralphmarondev.core.data.local.database.entities.UserEntity
import com.ralphmarondev.core.domain.model.User

fun User.toEntity(): UserEntity {
    return UserEntity(
        username = username,
        password = password,
        displayName = displayName
    )
}

fun UserEntity.toDomain(): User {
    return User(
        username = username,
        password = password,
        displayName = displayName
    )
}