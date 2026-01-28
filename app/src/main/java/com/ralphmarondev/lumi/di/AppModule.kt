package com.ralphmarondev.lumi.di

import com.ralphmarondev.boot.auth.di.authModule
import com.ralphmarondev.boot.setup.di.setupModule
import com.ralphmarondev.clock.di.clockModule
import com.ralphmarondev.core.di.coreModule
import com.ralphmarondev.launcher.di.launcherModule
import com.ralphmarondev.lumi.MainViewModel
import com.ralphmarondev.media.di.mediaModule
import com.ralphmarondev.notes.di.notesModule
import com.ralphmarondev.settings.di.settingsModule
import com.ralphmarondev.weather.di.weatherModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(coreModule)
    // system
    includes(authModule)
    includes(setupModule)
    includes(launcherModule)
    includes(settingsModule)
    includes(mediaModule)
    // miniapps
    includes(notesModule)
    includes(weatherModule)
    includes(clockModule)

    viewModelOf(::MainViewModel)
}