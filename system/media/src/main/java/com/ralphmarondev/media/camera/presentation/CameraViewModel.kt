package com.ralphmarondev.media.camera.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.RequiresPermission
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.FocusMeteringAction.Builder
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
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

    private var currentRecording: Recording? = null

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
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

                val focusAction = Builder(
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

            is CameraAction.SetVideoCapture -> {
                _state.update { it.copy(videoCapture = action.capture) }
            }

            CameraAction.StartVideoRecording -> {
                startVideoRecording()
            }

            CameraAction.StopVideoRecording -> {
                currentRecording?.stop()
                currentRecording = null
            }

            is CameraAction.ChangeMode -> {
                _state.update {
                    it.copy(mode = action.mode)
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

    @SuppressLint("CheckResult")
    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    private fun startVideoRecording() {
        val videoCapture = _state.value.videoCapture ?: return
        val context = getApplication<Application>()

        val videosDir = File(context.filesDir, "user/videos").apply { if (!exists()) mkdirs() }
        val videoFile = File(videosDir, "video_${System.currentTimeMillis()}.mp4")

        val pendingRecording = (videoCapture.output as? Recorder)?.prepareRecording(
            context,
            FileOutputOptions.Builder(videoFile).build()
        )?.withAudioEnabled(true) ?: return

        currentRecording = pendingRecording.start(ContextCompat.getMainExecutor(context)) { event ->
            when (event) {
                is VideoRecordEvent.Start -> _state.update { it.copy(isRecording = true) }
                is VideoRecordEvent.Finalize -> _state.update {
                    it.copy(
                        isRecording = false,
                        lastSavedVideoPath = videoFile.absolutePath
                    )
                }
            }
        }
    }
}