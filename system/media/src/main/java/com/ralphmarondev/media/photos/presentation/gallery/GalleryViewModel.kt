package com.ralphmarondev.media.photos.presentation.gallery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class GalleryViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(GalleryState())
    val state = _state.asStateFlow()

    private val imagesDir = File(application.filesDir, "user/images")

    init {
        onAction(GalleryAction.LoadImages)
    }

    fun onAction(action: GalleryAction) {
        when (action) {
            GalleryAction.LoadImages -> loadImages()
            GalleryAction.ResetNavigation -> {
                _state.update {
                    it.copy(
                        navigateToDetails = false,
                        selectedImagePath = ""
                    )
                }
            }

            is GalleryAction.SelectImage -> {
                _state.update {
                    it.copy(
                        selectedImagePath = action.path,
                        navigateToDetails = true
                    )
                }
            }
        }
    }

    private fun loadImages() {
        if (!imagesDir.exists()) {
            _state.update { it.copy(images = emptyList()) }
            return
        }

        val files =
            imagesDir.listFiles()?.sortedByDescending { it.lastModified() }?.map { it.absolutePath }
                ?: emptyList()
        _state.update { it.copy(images = files) }
    }
}