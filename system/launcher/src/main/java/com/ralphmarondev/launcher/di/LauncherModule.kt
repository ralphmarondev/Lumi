package com.ralphmarondev.launcher.di

import com.ralphmarondev.launcher.data.repository.LauncherRepositoryImpl
import com.ralphmarondev.launcher.domain.repository.LauncherRepository
import com.ralphmarondev.launcher.presentation.LauncherViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val launcherModule = module {
    singleOf(::LauncherRepositoryImpl).bind<LauncherRepository>()
    viewModelOf(::LauncherViewModel)
}