package com.ralphmarondev.media.photos.presentation.details

sealed interface DetailsAction {
    data class SetDeleteImageDialogValue(val value: Boolean) : DetailsAction
    data object DeleteImage : DetailsAction
    data object ShareImage : DetailsAction
    data object NavigateBack : DetailsAction
}