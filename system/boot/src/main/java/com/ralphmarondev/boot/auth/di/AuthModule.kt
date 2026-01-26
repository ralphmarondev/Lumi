package com.ralphmarondev.boot.auth.di

import com.ralphmarondev.boot.auth.data.repository.AuthRepositoryImpl
import com.ralphmarondev.boot.auth.domain.repository.AuthRepository
import com.ralphmarondev.boot.auth.presentation.login.LoginViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    viewModelOf(::LoginViewModel)
}