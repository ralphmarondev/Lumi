package com.ralphmarondev.media.di

import com.ralphmarondev.media.camera.di.cameraModule
import com.ralphmarondev.media.photos.di.photosModule
import org.koin.dsl.module

val mediaModule = module {
    includes(cameraModule)
    includes(photosModule)
}