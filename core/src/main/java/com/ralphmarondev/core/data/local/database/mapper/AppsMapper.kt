package com.ralphmarondev.core.data.local.database.mapper

import com.ralphmarondev.core.data.local.database.entities.AppsEntity
import com.ralphmarondev.core.domain.model.Apps

fun Apps.toEntity(): AppsEntity {
    return AppsEntity(
        id = id,
        name = name,
        icon = icon,
        versionName = versionName,
        versionCode = versionCode,
        isSystemApp = isSystemApp,
        isInstalled = isInstalled,
        isDocked = isDocked,
        order = order,
        tag = tag
    )
}

fun AppsEntity.toDomain(): Apps {
    return Apps(
        id = id,
        name = name,
        icon = icon,
        versionName = versionName,
        versionCode = versionCode,
        isSystemApp = isSystemApp,
        isInstalled = isInstalled,
        isDocked = isDocked,
        order = order,
        tag = tag
    )
}