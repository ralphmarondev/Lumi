package com.ralphmarondev.launcher.data.repository

import com.ralphmarondev.core.data.local.database.dao.LumiAppDao
import com.ralphmarondev.core.data.local.database.dao.WallpaperDao
import com.ralphmarondev.core.data.local.database.mapper.toDomain
import com.ralphmarondev.core.data.local.preferences.LumiPreferences
import com.ralphmarondev.core.domain.model.LumiApp
import com.ralphmarondev.core.domain.model.Wallpaper
import com.ralphmarondev.launcher.domain.repository.LauncherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class LauncherRepositoryImpl(
    private val wallpaperDao: WallpaperDao,
    private val lumiAppDao: LumiAppDao,
    private val preferences: LumiPreferences
) : LauncherRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getActiveWallpaper(): Flow<Wallpaper> {
        return preferences.getSystemLauncherWallpaper()
            .flatMapLatest { activeId ->
                wallpaperDao.getById(activeId)
            }
            .map { it?.toDomain() ?: Wallpaper() }
    }

    override suspend fun getDockApps(): List<LumiApp> {
        return lumiAppDao.getDockApps().map { it.toDomain() }
    }

    override suspend fun getMiniApps(): List<LumiApp> {
        return lumiAppDao.getInstalledApps().map { it.toDomain() }
    }
}