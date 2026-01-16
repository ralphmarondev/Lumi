package com.ralphmarondev.weather.di

import com.ralphmarondev.weather.data.local.preferences.WeatherAppPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val weatherModule = module {
    single { WeatherAppPreferences(context = androidContext().applicationContext) }
}