package com.ralphmarondev.media.camera.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Cameraswitch
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.ralphmarondev.core.presentation.shell.LocalLumiShellState
import com.ralphmarondev.core.presentation.shell.LumiShellStyle
import org.koin.compose.viewmodel.koinViewModel
import java.io.File

@Composable
fun CameraScreenRoot() {
    val viewModel: CameraViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val shellState = LocalLumiShellState.current

    LaunchedEffect(Unit) {
        shellState.setAppearance(LumiShellStyle.WhiteOnTransparent)
    }

    CameraScreen(
        state = state,
        action = viewModel::onAction
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CameraScreen(
    state: CameraState,
    action: (CameraAction) -> Unit
) {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (hasCameraPermission) {
                CameraPreview(
                    lifeCycleOwner = lifeCycleOwner,
                    lensFacing = state.lensFacing,
                    action = action
                )
            }

            CameraControls(
                state = state,
                onCapture = { action(CameraAction.CaptureImage) },
                onSwitchCamera = { action(CameraAction.SwitchCamera) },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun CameraPreview(
    lifeCycleOwner: LifecycleOwner,
    lensFacing: Int,
    action: (CameraAction) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

                val capture = ImageCapture.Builder().build()
                action(CameraAction.SetImageCapture(capture))

                val selector = CameraSelector.Builder()
                    .requireLensFacing(lensFacing)
                    .build()

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifeCycleOwner,
                    selector,
                    preview,
                    capture
                )
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}

@Composable
private fun CameraControls(
    state: CameraState,
    onCapture: () -> Unit,
    onSwitchCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.Black.copy(alpha = 0.6f))
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            ) {
                state.lastSavedImagePath?.let {
                    AsyncImage(
                        model = File(it),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.White, CircleShape)
                    .clickable { onCapture() },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }
            IconButton(onClick = onSwitchCamera) {
                Icon(
                    imageVector = Icons.Outlined.Cameraswitch,
                    contentDescription = "Switch Camera",
                    tint = Color.White
                )
            }
        }
    }
}