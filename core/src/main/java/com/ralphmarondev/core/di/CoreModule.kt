package com.ralphmarondev.core.di

import com.ralphmarondev.core.data.local.database.LumiDatabase
import com.ralphmarondev.core.data.local.preferences.LumiPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single { LumiPreferences(context = androidContext().applicationContext) }
    single { LumiDatabase.createDatabase(androidContext()) }
    single { get<LumiDatabase>().userDao }
    single { get<LumiDatabase>().wallpaperDao }
    single { get<LumiDatabase>().lumiAppDao }
    single { get<LumiDatabase>().contactDao }
    single { get<LumiDatabase>().callHistoryDao }
}