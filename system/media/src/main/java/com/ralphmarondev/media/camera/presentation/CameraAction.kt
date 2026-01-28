package com.ralphmarondev.media.camera.presentation

import androidx.camera.core.ImageCapture

sealed interface CameraAction {
    data object CaptureImage : CameraAction
    data object SwitchCamera : CameraAction
    data class SetImageCapture(val imageCapture: ImageCapture) : CameraAction
}