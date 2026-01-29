package com.ralphmarondev.media.photos.presentation.details

sealed interface DetailsAction {
    data object DeleteImage : DetailsAction
    data object ShareImage : DetailsAction
}