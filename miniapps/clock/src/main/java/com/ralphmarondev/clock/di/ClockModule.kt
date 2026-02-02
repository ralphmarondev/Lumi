package com.ralphmarondev.clock.di

import com.ralphmarondev.clock.data.local.database.ClockAppDatabase
import com.ralphmarondev.clock.data.local.preferences.ClockAppPreferences
import com.ralphmarondev.clock.data.repository.AlarmRepositoryImpl
import com.ralphmarondev.clock.domain.repository.AlarmRepository
import com.ralphmarondev.clock.presentation.alarm.AlarmViewModel
import com.ralphmarondev.clock.presentation.new_alarm.NewAlarmViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val clockModule = module {
    single { ClockAppPreferences(context = androidContext().applicationContext) }
    single { ClockAppDatabase.createDatabase(androidContext()) }
    single { get<ClockAppDatabase>().alarmDao }
    single<AlarmRepository> { AlarmRepositoryImpl(get()) }

    viewModelOf(::AlarmViewModel)
    viewModelOf(::NewAlarmViewModel)
}