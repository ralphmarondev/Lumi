package com.ralphmarondev.system.di

import com.ralphmarondev.system.auth.di.authModule
import com.ralphmarondev.system.launcher.di.launcherModule
import com.ralphmarondev.system.settings.di.settingsModule
import com.ralphmarondev.system.setup.di.setupModule
import org.koin.dsl.module

val systemModule = module {
    includes(setupModule)
    includes(authModule)
    includes(launcherModule)
    includes(settingsModule)
}