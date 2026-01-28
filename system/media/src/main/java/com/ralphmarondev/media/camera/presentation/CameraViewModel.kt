package com.ralphmarondev.media.camera.presentation

import android.app.Application
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.ralphmarondev.core.common.FileManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class CameraViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    fun onAction(action: CameraAction) {
        when (action) {
            CameraAction.CaptureImage -> captureImage()
            CameraAction.SwitchCamera -> Unit
            is CameraAction.SetImageCapture -> {
                _state.update {
                    it.copy(imageCapture = action.imageCapture)
                }
            }
        }
    }

    private fun captureImage() {
        val imageCapture = _state.value.imageCapture ?: return
        val context = getApplication<Application>()

        val imagesDir = File(context.filesDir, "user/images").apply {
            if (!exists()) mkdirs()
        }

        val file = File(imagesDir, FileManager.generateFileName())
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(file).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    _state.update {
                        it.copy(
                            errorMessage = exception.message
                        )
                    }
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    _state.update {
                        it.copy(
                            lastSavedImagePath = file.absolutePath
                        )
                    }
                }
            }
        )
    }
}