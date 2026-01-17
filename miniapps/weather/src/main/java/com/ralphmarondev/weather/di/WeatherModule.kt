package com.ralphmarondev.weather.di

import com.ralphmarondev.weather.data.local.preferences.WeatherAppPreferences
import com.ralphmarondev.weather.data.network.ktor.KtorClient
import com.ralphmarondev.weather.data.repository.WeatherRepositoryImpl
import com.ralphmarondev.weather.domain.repository.WeatherRepository
import com.ralphmarondev.weather.presentation.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weatherModule = module {
    single { WeatherAppPreferences(context = androidContext().applicationContext) }
    single { KtorClient.create() }
    single<WeatherRepository> { WeatherRepositoryImpl() }

    viewModelOf(::HomeViewModel)
}