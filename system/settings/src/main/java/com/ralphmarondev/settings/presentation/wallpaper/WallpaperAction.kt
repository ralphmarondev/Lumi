package com.ralphmarondev.settings.presentation.wallpaper

sealed interface WallpaperAction {
    data class ChangeWallpaper(val value: Long) : WallpaperAction
    data object NavigateBack : WallpaperAction
    data object ResetNavigation : WallpaperAction
}