package com.ralphmarondev.media.photos.presentation.gallery

data class GalleryState(
    val images: List<String> = emptyList(),
    val errorMessage: String? = null
)