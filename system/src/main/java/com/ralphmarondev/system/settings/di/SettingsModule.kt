package com.ralphmarondev.system.settings.di

import com.ralphmarondev.system.settings.data.repository.SettingsRepositoryImpl
import com.ralphmarondev.system.settings.domain.repository.SettingsRepository
import com.ralphmarondev.system.settings.presentation.overview.OverviewViewModel
import com.ralphmarondev.system.settings.presentation.security.SecurityViewModel
import com.ralphmarondev.system.settings.presentation.wallpaper.WallpaperViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val settingsModule = module {
    singleOf(::SettingsRepositoryImpl).bind<SettingsRepository>()

    viewModelOf(::OverviewViewModel)
    viewModelOf(::WallpaperViewModel)
    viewModelOf(::SecurityViewModel)
}