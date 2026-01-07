package com.ralphmarondev.system.launcher.di

import com.ralphmarondev.system.launcher.presentation.LauncherViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val launcherModule = module {
    viewModelOf(::LauncherViewModel)
}