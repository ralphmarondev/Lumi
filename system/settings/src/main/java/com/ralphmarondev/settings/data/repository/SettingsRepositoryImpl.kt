package com.ralphmarondev.settings.data.repository

import android.util.Log
import com.ralphmarondev.core.data.local.database.dao.UserDao
import com.ralphmarondev.core.data.local.database.dao.WallpaperDao
import com.ralphmarondev.core.data.local.database.mapper.toDomain
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User
import com.ralphmarondev.core.domain.model.Wallpaper
import com.ralphmarondev.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val preferences: AppPreferences,
    private val userDao: UserDao,
    private val wallpaperDao: WallpaperDao
) : SettingsRepository {

    override suspend fun setEnableAuthKey(value: Boolean) {
        preferences.setSystemEnableAuth(value)
    }

    override suspend fun isAuthEnabled(): Boolean {
        return preferences.isSystemEnableAuth().first()
    }

    override suspend fun getUserInformation(): Result<User> {
        return try {
            val username = preferences.getSystemCurrentUser().first()
            val user = userDao.getByUsername(username)
            Result.Success(user?.toDomain() ?: User())
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed fetching user details.")
        }
    }

    override suspend fun updateUserInformation(user: User): Result<User> {
        return try {
            val username = preferences.getSystemCurrentUser().first()
            val existingUser = userDao.getByUsername(username)
                ?: return Result.Error("Failed updating user information.")

            val userEntity = existingUser.copy(
                displayName = user.displayName,
                username = user.username,
                email = user.email,
                phoneNumber = user.phoneNumber,
                gender = user.gender,
                birthday = user.birthday,
                profileImagePath = user.profileImagePath,
                updatedAt = System.currentTimeMillis()
            )
            userDao.update(userEntity)
            return Result.Success(userEntity.toDomain())
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed updating user information.")
        }
    }

    override suspend fun setActiveWallpaper(id: Long) {
        preferences.setSystemLauncherWallpaper(id)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getActiveWallpaper(): Flow<Wallpaper> {
        return preferences.getSystemLauncherWallpaper()
            .flatMapLatest { activeId ->
                wallpaperDao.getById(activeId)
            }
            .map {
                it?.toDomain() ?: Wallpaper()
            }
    }

    override suspend fun getAllWallpapers(): List<Wallpaper> {
        val wallpaperEntities = wallpaperDao.getAll()

        return wallpaperEntities.map { it.toDomain() }
    }

    override suspend fun updateDisplayName(displayName: String) {
        try {
            val username = preferences.getSystemCurrentUser().first()
            val userEntity = userDao.getByUsername(username)
                ?: return

            userDao.update(
                userEntity = userEntity.copy(
                    displayName = displayName
                )
            )
            Log.d("SettingsRepositoryImpl", "Display name updated successfully.")
        } catch (e: Exception) {
            Log.e("SettingsRepositoryImpl", "Error updating display name: ${e.message}")
        }
    }
}