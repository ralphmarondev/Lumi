package com.ralphmarondev.launcher.domain.repository

import com.ralphmarondev.core.domain.model.Apps
import com.ralphmarondev.core.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow

interface LauncherRepository {
    fun getActiveWallpaper(): Flow<Wallpaper>

    suspend fun getDockApps(): List<Apps>
    suspend fun getMiniApps(): List<Apps>
}