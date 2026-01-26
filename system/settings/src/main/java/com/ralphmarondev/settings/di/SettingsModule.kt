package com.ralphmarondev.settings.di

import com.ralphmarondev.settings.data.repository.SettingsRepositoryImpl
import com.ralphmarondev.settings.domain.repository.SettingsRepository
import com.ralphmarondev.settings.presentation.about.AboutViewModel
import com.ralphmarondev.settings.presentation.account.AccountViewModel
import com.ralphmarondev.settings.presentation.overview.OverviewViewModel
import com.ralphmarondev.settings.presentation.security.SecurityViewModel
import com.ralphmarondev.settings.presentation.wallpaper.WallpaperViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val settingsModule = module {
    singleOf(::SettingsRepositoryImpl).bind<SettingsRepository>()

    viewModelOf(::OverviewViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::WallpaperViewModel)
    viewModelOf(::SecurityViewModel)
    viewModelOf(::AboutViewModel)
}