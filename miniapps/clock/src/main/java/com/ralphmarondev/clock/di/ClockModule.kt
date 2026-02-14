package com.ralphmarondev.clock.di

import com.ralphmarondev.clock.ClockViewModel
import com.ralphmarondev.clock.data.local.database.ClockDatabase
import com.ralphmarondev.clock.data.local.preferences.ClockPreferences
import com.ralphmarondev.clock.data.repository.AlarmRepositoryImpl
import com.ralphmarondev.clock.domain.repository.AlarmRepository
import com.ralphmarondev.clock.presentation.alarm.AlarmViewModel
import com.ralphmarondev.clock.presentation.stopwatch.StopWatchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val clockModule = module {
    single { ClockPreferences(context = androidContext().applicationContext) }
    single { ClockDatabase.createDatabase(androidContext()) }
    single { get<ClockDatabase>().alarmDao }
    single<AlarmRepository> { AlarmRepositoryImpl(get()) }

    viewModelOf(::ClockViewModel)
    viewModelOf(::AlarmViewModel)
    viewModelOf(::StopWatchViewModel)
}