package com.ralphmarondev.media.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current

    var imageCapture: ImageCapture? by

}