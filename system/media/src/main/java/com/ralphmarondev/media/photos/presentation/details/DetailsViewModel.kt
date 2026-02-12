package com.ralphmarondev.media.photos.presentation.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class DetailsViewModel(
    application: Application,
    initialImagePath: String
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(DetailsState(imagePath = initialImagePath))
    val state = _state.asStateFlow()

    fun onAction(action: DetailsAction) {
        when (action) {
            DetailsAction.DeleteImage -> {
                deleteImage(imagePath = _state.value.imagePath.trim())
            }

            DetailsAction.ShareImage -> shareImage()
            is DetailsAction.SetDeleteImageDialogValue -> {
                _state.update { it.copy(showDeleteDialog = action.value) }
            }

            DetailsAction.NavigateBack -> {
                _state.update { it.copy(navigateBack = true) }
            }
        }
    }

    private fun deleteImage(imagePath: String) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(errorMessage = null, showErrorMessage = false) }
                val file = File(imagePath)
                if (file.exists() && file.delete()) {
                    _state.update { it.copy(imagePath = "", navigateBack = true) }
                } else {
                    _state.update {
                        it.copy(
                            errorMessage = "Failed to delete image",
                            showErrorMessage = true
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Failed deleting image. Error: ${e.message}",
                        showErrorMessage = true
                    )
                }
            }
        }
    }

    private fun shareImage() {
        Log.d("Details", "Share image.")
    }
}