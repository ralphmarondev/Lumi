package com.ralphmarondev.system.setup.di

import com.ralphmarondev.system.setup.presentation.SetupViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val setupModule = module {
    viewModelOf(::SetupViewModel)
}