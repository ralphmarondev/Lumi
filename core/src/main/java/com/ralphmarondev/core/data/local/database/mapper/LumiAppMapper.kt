package com.ralphmarondev.core.data.local.database.mapper

import com.ralphmarondev.core.data.local.database.entities.LumiAppEntity
import com.ralphmarondev.core.domain.model.LumiApp

fun LumiApp.toEntity(): LumiAppEntity {
    return LumiAppEntity(
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

fun LumiAppEntity.toDomain(): LumiApp {
    return LumiApp(
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