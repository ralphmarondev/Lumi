package com.ralphmarondev.boot.setup.data.repository

import android.content.Context
import com.ralphmarondev.boot.R
import com.ralphmarondev.boot.setup.domain.repository.SetupRepository
import com.ralphmarondev.core.common.FileManager
import com.ralphmarondev.core.data.local.database.dao.UserDao
import com.ralphmarondev.core.data.local.database.dao.WallpaperDao
import com.ralphmarondev.core.data.local.database.entities.WallpaperEntity
import com.ralphmarondev.core.data.local.database.mapper.toDomain
import com.ralphmarondev.core.data.local.database.mapper.toEntity
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.core.domain.model.Language
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User

class SetupRepositoryImpl(
    private val userDao: UserDao,
    private val wallpaperDao: WallpaperDao,
    private val preferences: AppPreferences,
    private val context: Context
) : SetupRepository {
    override suspend fun getUserByUsername(username: String): User? {
        return userDao.getByUsername(username)?.toDomain()
    }

    override suspend fun setup(
        language: Language,
        username: String,
        password: String
    ): Result<User>? {
        return try {
            val existingUser = userDao.getByUsername(username)
            if (existingUser != null) {
                return Result.Error("Username already exists")
            }

            val profileImagePath = FileManager.saveFromDrawable(
                context = context,
                drawableResId = R.drawable.ralphmaron,
                directory = FileManager.Directory.PROFILE,
                fileName = "profile_default.jpg"
            )

            val user = User(
                username = username,
                password = password,
                profileImagePath = profileImagePath
            )
            val userEntityId = userDao.create(user.toEntity())
            val savedUserEntity = userDao.getById(userEntityId)
                ?: return Result.Error("Failed creating user")

            val savedUser = savedUserEntity.toDomain()

            val wallpaper1Path = FileManager.saveFromDrawable(
                context = context,
                drawableResId = R.drawable.wallpaper1,
                directory = FileManager.Directory.WALLPAPER,
                fileName = "wallpaper1.jpg"
            )
            val wallpaper2Path = FileManager.saveFromDrawable(
                context = context,
                drawableResId = R.drawable.wallpaper2,
                directory = FileManager.Directory.WALLPAPER,
                fileName = "wallpaper2.jpg"
            )
            wallpaperDao.create(
                wallpaperEntity = WallpaperEntity(
                    path = wallpaper1Path,
                    owner = savedUser.username
                )
            )
            wallpaperDao.create(
                wallpaperEntity = WallpaperEntity(
                    path = wallpaper2Path,
                    owner = savedUser.username
                )
            )

            preferences.setSystemLanguage(language.code)
            preferences.setSystemOnboardingCompleted(true)
            Result.Success(data = savedUser)
        } catch (e: Exception) {
            Result.Error(message = e.localizedMessage)
        }
    }
}