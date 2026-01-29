package com.ralphmarondev.media.camera.presentation

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture

data class CameraState(
    val imageCapture: ImageCapture? = null,
    val lastSavedImagePath: String? = null,
    val errorMessage: String? = null,
    val lensFacing: Int = CameraSelector.LENS_FACING_BACK
)