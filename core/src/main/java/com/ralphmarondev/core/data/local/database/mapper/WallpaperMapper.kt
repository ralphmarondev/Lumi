package com.ralphmarondev.core.data.local.database.mapper

import com.ralphmarondev.core.data.local.database.entities.WallpaperEntity
import com.ralphmarondev.core.domain.model.Wallpaper

fun WallpaperEntity.toDomain(): Wallpaper {
    return Wallpaper(
        id = id,
        path = path,
        owner = owner
    )
}

fun Wallpaper.toEntity(): WallpaperEntity {
    return WallpaperEntity(
        id = id,
        path = path,
        owner = owner
    )
}