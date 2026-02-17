package com.ralphmarondev.boot.setup.data.repository

import android.content.Context
import android.util.Log
import com.ralphmarondev.boot.R
import com.ralphmarondev.boot.setup.domain.model.SetupResult
import com.ralphmarondev.boot.setup.domain.repository.SetupRepository
import com.ralphmarondev.core.common.FileManager
import com.ralphmarondev.core.data.local.database.dao.LumiAppDao
import com.ralphmarondev.core.data.local.database.dao.UserDao
import com.ralphmarondev.core.data.local.database.dao.WallpaperDao
import com.ralphmarondev.core.data.local.database.entities.LumiAppEntity
import com.ralphmarondev.core.data.local.database.entities.WallpaperEntity
import com.ralphmarondev.core.data.local.database.mapper.toDomain
import com.ralphmarondev.core.data.local.database.mapper.toEntity
import com.ralphmarondev.core.data.local.preferences.LumiPreferences
import com.ralphmarondev.core.domain.model.LumiAppTag
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User

class SetupRepositoryImpl(
    private val userDao: UserDao,
    private val wallpaperDao: WallpaperDao,
    private val lumiAppDao: LumiAppDao,
    private val preferences: LumiPreferences,
    private val context: Context
) : SetupRepository {
    override suspend fun setup(setupResult: SetupResult) {
        val user = User(
            language = setupResult.selectedLanguage,
            displayName = setupResult.displayName,
            username = setupResult.username,
            password = setupResult.password
        )
        val savedUser = saveUser(user)
            ?: throw IllegalStateException("User not saved")
        saveDefaultWallpapers(savedUser.username)
        saveDefaultApps()

        preferences.setSystemLanguage(setupResult.selectedLanguage)
        preferences.setSystemOnboardingCompleted(true)
        Result.Success(data = savedUser)
    }

    private suspend fun saveUser(user: User): User? {
        Log.d("Setup", "Saving user...")
        val profileImagePath = FileManager.saveFromDrawable(
            context = context,
            drawableResId = R.drawable.ralphmaron,
            directory = FileManager.Directory.PROFILE,
            fileName = "profile_default.jpg"
        )
        val userEntityId = userDao.create(user.toEntity())
        val savedUserEntity = userDao.getById(userEntityId)
        return savedUserEntity?.toDomain()?.copy(profileImagePath = profileImagePath)
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
            LumiAppEntity(
                name = "Settings",
                versionName = "1.0 Settings",
                versionCode = 1,
                isSystemApp = true,
                isDocked = true,
                order = 1,
                tag = LumiAppTag.Settings.name,
                resId = R.drawable.setting
            ),
            LumiAppEntity(
                name = "Notes",
                versionName = "1.0 Notes",
                versionCode = 1,
                isSystemApp = false,
                isDocked = true,
                order = 2,
                tag = LumiAppTag.Notes.name,
                resId = R.drawable.notepad
            ),
            LumiAppEntity(
                name = "Clock",
                versionName = "1.0 Clock",
                versionCode = 1,
                isSystemApp = false,
                isDocked = true,
                order = 3,
                tag = LumiAppTag.Clock.name,
                resId = R.drawable.clock
            ),
            LumiAppEntity(
                name = "Weather",
                versionName = "1.0 Weather",
                versionCode = 1,
                isSystemApp = false,
                isDocked = true,
                order = 4,
                tag = LumiAppTag.Weather.name,
                resId = R.drawable.weather
            ),
            LumiAppEntity(
                name = "Calendar",
                versionName = "1.0 Calendar",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 5,
                tag = LumiAppTag.Calendar.name,
                resId = R.drawable.calendar
            ),
            LumiAppEntity(
                name = "Camera",
                versionName = "1.0 Camera",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 6,
                tag = LumiAppTag.Camera.name,
                resId = R.drawable.camera
            ),
            LumiAppEntity(
                name = "Photos",
                versionName = "1.0 Photos",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 7,
                tag = LumiAppTag.Photos.name,
                resId = R.drawable.photos
            ),
            LumiAppEntity(
                name = "Videos",
                versionName = "1.0 Videos",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 8,
                tag = LumiAppTag.Videos.name,
                resId = R.drawable.video
            ),
            LumiAppEntity(
                name = "Contacts",
                versionName = "1.0 Contacts",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 9,
                tag = LumiAppTag.Contacts.name,
                resId = R.drawable.contacts
            ),
            LumiAppEntity(
                name = "Phone",
                versionName = "1.0 Phone",
                versionCode = 1,
                isSystemApp = true,
                isDocked = false,
                order = 10,
                tag = LumiAppTag.Phone.name,
                resId = R.drawable.phone_call
            ),
            LumiAppEntity(
                name = "Messages",
                versionName = "1.0 Message",
                versionCode = 1,
                isSystemApp = true,
                isDocked = false,
                order = 13,
                tag = LumiAppTag.Message.name,
                resId = R.drawable.messages
            ),
            LumiAppEntity(
                name = "Community",
                versionName = "1.0 Community",
                versionCode = 1,
                isSystemApp = true,
                isDocked = false,
                order = 11,
                tag = LumiAppTag.Community.name,
                resId = R.drawable.community
            ),
            LumiAppEntity(
                name = "Calculator",
                versionName = "1.0 Calculator",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 12,
                tag = LumiAppTag.Calculator.name,
                resId = R.drawable.calculator
            ),
            LumiAppEntity(
                name = "Lumi Store",
                versionName = "1.0 Lumi Store",
                versionCode = 1,
                isSystemApp = true,
                isDocked = false,
                order = 14,
                tag = LumiAppTag.AppStore.name,
                resId = R.drawable.app_store
            ),
            LumiAppEntity(
                name = "Browser",
                versionName = "1.0 Browser",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 15,
                tag = LumiAppTag.Browser.name,
                resId = R.drawable.browser,
                isInstalled = false
            ),
            LumiAppEntity(
                name = "Finances",
                versionName = "1.0 Finances",
                versionCode = 1,
                isSystemApp = false,
                isDocked = false,
                order = 16,
                tag = LumiAppTag.Finances.name,
                resId = R.drawable.finances,
                isInstalled = false
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
            lumiAppDao.create(newApp)
        }
        Log.d("Setup", "Default apps installed.")
    }
}