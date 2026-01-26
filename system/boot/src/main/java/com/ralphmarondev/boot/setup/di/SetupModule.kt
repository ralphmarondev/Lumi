package com.ralphmarondev.boot.setup.di

import com.ralphmarondev.boot.setup.data.repository.SetupRepositoryImpl
import com.ralphmarondev.boot.setup.domain.repository.SetupRepository
import com.ralphmarondev.boot.setup.presentation.SetupViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val setupModule = module {
    singleOf(::SetupRepositoryImpl).bind<SetupRepository>()
    viewModelOf(::SetupViewModel)
}