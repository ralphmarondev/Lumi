package com.ralphmarondev.media.videos.presentation.video_player

import android.net.Uri
import androidx.media3.exoplayer.ExoPlayer

data class VideoPlayerState(
    val player: ExoPlayer? = null,
    val videoUri: Uri? = null,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L
)