package com.ralphmarondev.media.photos.presentation.details

data class DetailsState(
    val imagePath: String = "",
    val errorMessage: String? = null,
    val showErrorMessage: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val navigateBack: Boolean = false
)