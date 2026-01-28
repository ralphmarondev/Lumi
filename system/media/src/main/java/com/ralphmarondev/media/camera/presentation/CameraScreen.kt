package com.ralphmarondev.media.camera.presentation

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CameraScreenRoot() {
    val viewModel: CameraViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    CameraScreen(
        state = state,
        action = viewModel::onAction
    )
}

@Composable
private fun CameraScreen(
    state: CameraState,
    action: (CameraAction) -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    Box(modifier = Modifier.fillMaxSize()) {
        @Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val capture = ImageCapture.Builder().build()
                    action(CameraAction.SetImageCapture(capture))
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifeCycleOwner,
                        cameraSelector,
                        preview,
                        capture
                    )
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            }
        )

        FloatingActionButton(
            onClick = { action(CameraAction.CaptureImage) }
        ) {
            Icon(
                imageVector = Icons.Outlined.CameraAlt,
                contentDescription = "Capture Image"
            )
        }
    }
}