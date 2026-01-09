package com.ralphmarondev.system.settings.di

import com.ralphmarondev.system.settings.presentation.overview.OverviewViewModel
import com.ralphmarondev.system.settings.presentation.wallpaper.WallpaperViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::OverviewViewModel)
    viewModelOf(::WallpaperViewModel)
}