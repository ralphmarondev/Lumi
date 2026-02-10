package com.ralphmarondev.media.videos.presentation.video_player

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VideoPlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(VideoPlayerState())
    val state: StateFlow<VideoPlayerState> = _state

    fun onAction(action: VideoPlayerAction) {
        when (action) {
            is VideoPlayerAction.LoadVideo -> loadVideo(action.uri)
            is VideoPlayerAction.PlayPause -> togglePlayPause()
            is VideoPlayerAction.SeekForward -> seekBy(10_000L)
            is VideoPlayerAction.SeekBackward -> seekBy(-10_000L)
            is VideoPlayerAction.SetPlayer -> _state.value =
                _state.value.copy(player = action.player)
        }
    }

    private fun loadVideo(uri: Uri) {
        _state.value.player?.release()

        val context = getApplication<Application>()
        val player = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = true
        }

        _state.value = VideoPlayerState(
            player = player,
            videoUri = uri,
            isPlaying = true,
            duration = player.duration
        )
    }

    private fun togglePlayPause() {
        val player = _state.value.player ?: return
        if (player.isPlaying) player.pause() else player.play()
        _state.value = _state.value.copy(isPlaying = player.isPlaying)
    }

    private fun seekBy(millis: Long) {
        val player = _state.value.player ?: return
        val newPosition = (player.currentPosition + millis).coerceIn(0, player.duration)
        player.seekTo(newPosition)
        _state.value = _state.value.copy(currentPosition = newPosition)
    }

    override fun onCleared() {
        super.onCleared()
        _state.value.player?.release()
    }
}
