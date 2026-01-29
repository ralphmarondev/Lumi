package com.ralphmarondev.media.photos.di

import com.ralphmarondev.media.photos.presentation.gallery.GalleryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val photosModule = module {
    viewModelOf(::GalleryViewModel)
}