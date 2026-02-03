package com.ralphmarondev.media.camera.presentation

import androidx.camera.core.Camera
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView

sealed interface CameraAction {
    data object CaptureImage : CameraAction
    data object SwitchCamera : CameraAction
    data class SetImageCapture(val imageCapture: ImageCapture) : CameraAction
    data class SetCamera(val camera: Camera) : CameraAction
    data class SetPreviewView(val previewView: PreviewView) : CameraAction
    data class Focus(val x: Float, val y: Float) : CameraAction
}