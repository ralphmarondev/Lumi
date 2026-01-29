package com.ralphmarondev.media.photos.presentation.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class DetailsViewModel(
    application: Application,
    private val initialImagePath: String
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(DetailsState(imagePath = initialImagePath))
    val state = _state.asStateFlow()

    fun onAction(action: DetailsAction) {
        when (action) {
            DetailsAction.DeleteImage -> deleteImage()
            DetailsAction.ShareImage -> shareImage()
        }
    }

    private fun deleteImage() {
        val file = File(_state.value.imagePath)
        if (file.exists() && file.delete()) {
            _state.update {
                it.copy(errorMessage = null, imagePath = "")
            }
        } else {
            _state.update {
                it.copy(errorMessage = "Failed to delete image")
            }
        }
    }

    private fun shareImage() {
        Log.d("Details", "Share image.")
    }
}