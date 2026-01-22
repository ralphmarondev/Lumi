package com.ralphmarondev.system.settings.domain.repository

import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User
import com.ralphmarondev.core.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setEnableAuthKey(value: Boolean)

    suspend fun isAuthEnabled(): Boolean

    suspend fun getUserInformation(): Result<User>

    suspend fun updateUserInformation(user: User): Result<User>

    suspend fun setActiveWallpaper(id: Long)

    fun getActiveWallpaper(): Flow<Wallpaper>

    suspend fun getAllWallpapers(): List<Wallpaper>
}