package com.ralphmarondev.media.videos.presentation.video_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class VideoListViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(VideoListState())
    val state: StateFlow<VideoListState> = _state

    init {
        loadVideos()
    }

    fun onAction(action: VideoListAction) {
        when (action) {
            is VideoListAction.LoadVideos -> loadVideos()
            is VideoListAction.PlayVideo -> playVideo(action.video)
            VideoListAction.ResetNavigation -> {
                _state.update {
                    it.copy(playVideo = false)
                }
            }
        }
    }

    private fun loadVideos() {
        viewModelScope.launch {
            val videosDir = File(getApplication<Application>().filesDir, "user/videos")
            if (!videosDir.exists()) videosDir.mkdirs()

            val videoFiles = videosDir.listFiles { file ->
                file.extension.lowercase() in listOf("mp4", "mkv", "webm")
            }?.map { file ->
                VideoItem(
                    file = file,
                    name = file.name,
                    dateCaptured = file.lastModified()
                )
            } ?: emptyList()

            _state.value =
                _state.value.copy(videos = videoFiles.sortedByDescending { it.dateCaptured })
        }
    }

    private fun playVideo(video: VideoItem) {
        _state.update {
            it.copy(
                selectedVideoPath = video.file.absolutePath,
                playVideo = true
            )
        }
    }
}