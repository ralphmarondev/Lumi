package com.ralphmarondev.system.launcher.di

import com.ralphmarondev.system.launcher.data.repository.LauncherRepositoryImpl
import com.ralphmarondev.system.launcher.domain.repository.LauncherRepository
import com.ralphmarondev.system.launcher.presentation.LauncherViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val launcherModule = module {
    singleOf(::LauncherRepositoryImpl).bind<LauncherRepository>()
    viewModelOf(::LauncherViewModel)
}