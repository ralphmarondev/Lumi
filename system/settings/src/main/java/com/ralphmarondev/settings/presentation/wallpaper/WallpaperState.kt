package com.ralphmarondev.settings.presentation.wallpaper

import com.ralphmarondev.core.domain.model.Wallpaper

data class WallpaperState(
    val wallpapers: List<Wallpaper> = emptyList(),
    val activeWallpaper: Long = -1,
    val navigateBack: Boolean = false
)
