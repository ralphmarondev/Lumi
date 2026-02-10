package com.ralphmarondev.media.videos.presentation.video_player

import android.net.Uri
import androidx.media3.exoplayer.ExoPlayer

sealed class VideoPlayerAction {
    object PlayPause : VideoPlayerAction()
    object SeekForward : VideoPlayerAction()
    object SeekBackward : VideoPlayerAction()
    data class SetPlayer(val player: ExoPlayer) : VideoPlayerAction()
    data class LoadVideo(val uri: Uri) : VideoPlayerAction()
}