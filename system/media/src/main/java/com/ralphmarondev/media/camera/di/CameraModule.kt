package com.ralphmarondev.media.camera.di

import com.ralphmarondev.media.camera.presentation.CameraViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cameraModule = module {
    viewModelOf(::CameraViewModel)
}