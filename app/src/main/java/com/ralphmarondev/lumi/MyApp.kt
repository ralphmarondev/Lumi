package com.ralphmarondev.lumi

import android.app.Application
import com.ralphmarondev.lumi.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(androidContext = this@MyApp)
            modules(modules = appModule)
        }
    }
}