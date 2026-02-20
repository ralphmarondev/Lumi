package com.ralphmarondev.lumi.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ralphmarondev.boot.auth.di.authModule
import com.ralphmarondev.boot.setup.di.setupModule
import com.ralphmarondev.calculator.di.calculatorModule
import com.ralphmarondev.calendar.di.calendarModule
import com.ralphmarondev.clock.di.clockModule
import com.ralphmarondev.community.di.communityModule
import com.ralphmarondev.core.di.coreModule
import com.ralphmarondev.launcher.di.launcherModule
import com.ralphmarondev.lumi.MainViewModel
import com.ralphmarondev.media.di.mediaModule
import com.ralphmarondev.notes.di.notesModule
import com.ralphmarondev.settings.di.settingsModule
import com.ralphmarondev.store.di.storeModule
import com.ralphmarondev.telephony.di.telephonyModule
import com.ralphmarondev.weather.di.weatherModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(coreModule)
    // system
    includes(authModule)
    includes(setupModule)
    includes(launcherModule)
    includes(settingsModule)
    includes(mediaModule)
    includes(telephonyModule)
    includes(storeModule)
    // miniapps
    includes(notesModule)
    includes(weatherModule)
    includes(clockModule)
    includes(calendarModule)
    includes(calculatorModule)
    includes(communityModule)

    viewModelOf(::MainViewModel)
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
}