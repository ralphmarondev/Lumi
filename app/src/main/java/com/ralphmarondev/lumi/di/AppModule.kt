package com.ralphmarondev.lumi.di

import com.ralphmarondev.core.di.coreModule
import com.ralphmarondev.notes.di.notesModule
import com.ralphmarondev.system.di.systemModule
import org.koin.dsl.module

val appModule = module {
    includes(coreModule)
    includes(systemModule)
    includes(notesModule)
}