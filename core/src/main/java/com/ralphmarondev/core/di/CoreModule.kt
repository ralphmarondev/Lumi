package com.ralphmarondev.core.di

import com.ralphmarondev.core.data.local.database.AppDatabase
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single { AppPreferences(context = androidContext().applicationContext) }
    single { AppDatabase.createDatabase(androidContext()) }
    single { get<AppDatabase>().userDao }
    single { get<AppDatabase>().wallpaperDao }
    single { get<AppDatabase>().appsDao }
    single { get<AppDatabase>().contactDao }
}