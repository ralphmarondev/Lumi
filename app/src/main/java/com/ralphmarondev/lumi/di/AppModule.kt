package com.ralphmarondev.lumi.di

import com.ralphmarondev.core.di.coreModule
import com.ralphmarondev.lumi.MainViewModel
import com.ralphmarondev.notes.di.notesModule
import com.ralphmarondev.system.di.systemModule
import com.ralphmarondev.weather.di.weatherModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(coreModule)
    includes(systemModule)
    includes(notesModule)
    includes(weatherModule)

    viewModelOf(::MainViewModel)
}