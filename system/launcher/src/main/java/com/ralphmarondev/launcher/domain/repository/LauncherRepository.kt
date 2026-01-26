package com.ralphmarondev.launcher.domain.repository

import com.ralphmarondev.core.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow

interface LauncherRepository {
    fun getActiveWallpaper(): Flow<Wallpaper>
}