package com.ralphmarondev.media.camera.presentation

import android.app.Application
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.compose.ui.geometry.Offset
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.ralphmarondev.core.common.FileManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.util.concurrent.TimeUnit

class CameraViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    fun onAction(action: CameraAction) {
        when (action) {
            CameraAction.CaptureImage -> captureImage()
            CameraAction.SwitchCamera -> {
                _state.update {
                    val newLens = if (it.lensFacing == CameraSelector.LENS_FACING_BACK) {
                        CameraSelector.LENS_FACING_FRONT
                    } else {
                        CameraSelector.LENS_FACING_BACK
                    }
                    it.copy(lensFacing = newLens)
                }
            }

            is CameraAction.SetImageCapture -> {
                _state.update {
                    it.copy(imageCapture = action.imageCapture)
                }
            }

            is CameraAction.Focus -> {
                val camera = _state.value.camera ?: return
                val previewView = _state.value.previewView ?: return

                val factory = SurfaceOrientedMeteringPointFactory(
                    previewView.width.toFloat(),
                    previewView.height.toFloat()
                )
                val point = factory.createPoint(action.x, action.y)

                val focusAction = FocusMeteringAction.Builder(
                    point,
                    FocusMeteringAction.FLAG_AF
                ).setAutoCancelDuration(2, TimeUnit.SECONDS).build()

                camera.cameraControl.startFocusAndMetering(focusAction)
                _state.update {
                    it.copy(focusPoint = Offset(action.x, action.y))
                }
            }

            is CameraAction.SetCamera -> {
                _state.update {
                    it.copy(camera = action.camera)
                }
            }

            is CameraAction.SetPreviewView -> {
                _state.update {
                    it.copy(previewView = action.previewView)
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