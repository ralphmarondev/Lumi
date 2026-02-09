package com.ralphmarondev.media.di

import com.ralphmarondev.media.camera.di.cameraModule
import com.ralphmarondev.media.photos.di.photosModule
import com.ralphmarondev.media.videos.di.videosModule
import org.koin.dsl.module

val mediaModule = module {
    includes(cameraModule)
    includes(photosModule)
    includes(videosModule)
}