package com.ralphmarondev.media.videos.presentation.video_player

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreenRoot(
    videoPath: String,
    navigateBack: () -> Unit
) {
    val viewModel: VideoPlayerViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    var controlsVisible by remember { mutableStateOf(true) }

    LaunchedEffect(controlsVisible) {
        if (controlsVisible) {
            delay(3000)
            controlsVisible = false
        }
    }

    LaunchedEffect(videoPath) {
        viewModel.onAction(VideoPlayerAction.LoadVideo(videoPath.toUri()))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Video Player") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            state.player?.let {
                                it.stop()
                                it.release()
                            }
                            navigateBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clickable { controlsVisible = !controlsVisible },
            contentAlignment = Alignment.Center
        ) {
            state.player?.let { player ->
                VideoPlayerView(player)
                ControlsOverlay(
                    isVisible = controlsVisible,
                    isPlaying = state.isPlaying,
                    onPlayPause = { viewModel.onAction(VideoPlayerAction.PlayPause) },
                    onSeekForward = { viewModel.onAction(VideoPlayerAction.SeekForward) },
                    onSeekBackward = { viewModel.onAction(VideoPlayerAction.SeekBackward) }
                )
            } ?: Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading video...", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun VideoPlayerView(player: ExoPlayer) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                this.player = player
                useController = false
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun ControlsOverlay(
    isVisible: Boolean,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onSeekForward: () -> Unit,
    onSeekBackward: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier
            .fillMaxSize()
            .zIndex(10f)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                IconButton(onClick = onSeekBackward) {
                    Icon(
                        imageVector = Icons.Outlined.PlayArrow,
                        contentDescription = "Rewind 10s",
                        modifier = Modifier
                            .rotate(180f)
                            .size(32.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = onPlayPause) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Outlined.Pause else Icons.Outlined.PlayArrow,
                        contentDescription = "Play/Pause",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = onSeekForward) {
                    Icon(
                        imageVector = Icons.Outlined.PlayArrow,
                        contentDescription = "Forward 10s",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}