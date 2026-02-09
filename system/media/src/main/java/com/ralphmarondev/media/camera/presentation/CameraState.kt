package com.ralphmarondev.media.camera.presentation

import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.compose.ui.geometry.Offset

enum class CameraMode { PHOTO, VIDEO, PORTRAIT, MORE }

data class CameraState(
    val imageCapture: ImageCapture? = null,
    val videoCapture: VideoCapture<*>? = null,
    val lastSavedImagePath: String? = null,
    val lastSavedVideoPath: String? = null,
    val errorMessage: String? = null,
    val lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    val camera: Camera? = null,
    val previewView: PreviewView? = null,
    val focusPoint: Offset? = null,
    val isRecording: Boolean = false,
    val mode: CameraMode = CameraMode.PHOTO
)