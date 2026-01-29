package com.ralphmarondev.media.photos.presentation.gallery

sealed interface GalleryAction {
    data class SelectImage(val path: String) : GalleryAction
    data object LoadImages : GalleryAction
}
