package com.ralphmarondev.boot.setup.data.repository

import android.content.Context
import android.util.Log
import com.ralphmarondev.boot.R
import com.ralphmarondev.boot.setup.domain.repository.SetupRepository
import com.ralphmarondev.core.common.FileManager
import com.ralphmarondev.core.data.local.database.dao.AppsDao
import com.ralphmarondev.core.data.local.database.dao.UserDao
import com.ralphmarondev.core.data.local.database.dao.WallpaperDao
import com.ralphmarondev.core.data.local.database.entities.AppsEntity
import com.ralphmarondev.core.data.local.database.entities.WallpaperEntity
import com.ralphmarondev.core.data.local.database.mapper.toDomain
import com.ralphmarondev.core.data.local.database.mapper.toEntity
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.core.domain.model.ApplicationTag
import com.ralphmarondev.core.domain.model.Language
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User

class SetupRepositoryImpl(
    private val userDao: UserDao,
    private val wallpaperDao: WallpaperDao,
    private val appsDao: AppsDao,
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
            Log.d("Setup", "Saving user.")
            val existingUser = userDao.getByUsername(username)
            if (existingUser != null) {
                Log.e("Setup", "Username already exists")
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
            Log.d("Setup", "User created successfully.")

            val savedUser = savedUserEntity.toDomain()

            saveDefaultWallpapers(savedUser.username)
            saveDefaultApps()

            preferences.setSystemLanguage(language.code)
            preferences.setSystemOnboardingCompleted(true)
            Result.Success(data = savedUser)
        } catch (e: Exception) {
            Log.e("Setup", "Error: ${e.message}")
            Result.Error(message = e.localizedMessage)
        }
    }

    private suspend fun saveDefaultWallpapers(ownerUsername: String) {
        Log.d("Setup", "Saving default wallpapers...")
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
                owner = ownerUsername
            )
        )
        wallpaperDao.create(
            wallpaperEntity = WallpaperEntity(
                path = wallpaper2Path,
                owner = ownerUsername
            )
        )
        Log.d("Setup", "Default wallpapers saved.")
    }

    private suspend fun saveDefaultApps() {
        val apps = listOf(
            AppsEntity(
                name = "Settings",
                versionName = "1.0 Settings",
                versionCode = 1,
                isSystemApp = true,
                isDocked = true,
                order = 1,
                tag = ApplicationTag.Settings.name,
                resId = R.drawable.setting
            ),
            AppsEntity(
                name = "Notes",
                versionName = "1.0 Notes",
                versionCode = 1,
                isSystemApp = false,
                isDocked = true,
                order = 2,
                tag = ApplicationTag.Notes.name,
                resId = R.drawable.notepad
            ),
            AppsEntity(
                name = "Clock",
                versionName = "1.0 Clock",
                versionCode = 1,
                isSystemApp = false,
                isDocked = true,
                order = 3,
                tag = ApplicationTag.Clock.name,
                resId = R.drawable.clock
            ),
            AppsEntity(
                name = "Weather",
                versionName = "1.0 Weather",
                versionCode = 1,
                isSystemApp = false,
                isDocked = true,
                order = 4,
                tag = ApplicationTag.Weather.name,
                resId = R.drawable.weather
            ),
            AppsEntity(
                name = "Calendar",
                versionName = "1.0 Calendar",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 5,
                tag = ApplicationTag.Calendar.name,
                resId = R.drawable.calendar
            ),
            AppsEntity(
                name = "Camera",
                versionName = "1.0 Camera",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 6,
                tag = ApplicationTag.Camera.name,
                resId = R.drawable.camera
            ),
            AppsEntity(
                name = "Photos",
                versionName = "1.0 Photos",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 7,
                tag = ApplicationTag.Photos.name,
                resId = R.drawable.photos
            ),
            AppsEntity(
                name = "Videos",
                versionName = "1.0 Videos",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 8,
                tag = ApplicationTag.Videos.name,
                resId = R.drawable.video
            ),
            AppsEntity(
                name = "Contacts",
                versionName = "1.0 Contacts",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 9,
                tag = ApplicationTag.Contacts.name,
                resId = R.drawable.contacts
            ),
            AppsEntity(
                name = "Phone",
                versionName = "1.0 Phone",
                versionCode = 1,
                isSystemApp = true,
                isDocked = false,
                order = 10,
                tag = ApplicationTag.Phone.name,
                resId = R.drawable.phone_call
            ),
            AppsEntity(
                name = "Community",
                versionName = "1.0 Community",
                versionCode = 1,
                isSystemApp = true,
                isDocked = false,
                order = 11,
                tag = ApplicationTag.Community.name,
                resId = R.drawable.community
            ),
            AppsEntity(
                name = "Calculator",
                versionName = "1.0 Calculator",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 12,
                tag = ApplicationTag.Calculator.name,
                resId = R.drawable.calculator
            )
        )

        Log.d("Setup", "Installing default apps...")
        apps.forEach { app ->
            val iconPath = FileManager.saveFromDrawable(
                context = context,
                drawableResId = app.resId,
                directory = FileManager.Directory.APPS,
                fileName = "${app.tag}.jpg"
            )
            val newApp = app.copy(
                icon = iconPath
            )
            appsDao.create(newApp)
        }
        Log.d("Setup", "Default apps installed.")
    }
}