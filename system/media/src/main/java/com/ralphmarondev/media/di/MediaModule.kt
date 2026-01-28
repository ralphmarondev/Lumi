package com.ralphmarondev.media.di

import com.ralphmarondev.media.camera.di.cameraModule
import org.koin.dsl.module

val mediaModule = module {
    includes(cameraModule)
}