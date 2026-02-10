package com.ralphmarondev.media.videos.presentation.video_list

import java.io.File

data class VideoListState(
    val videos: List<VideoItem> = emptyList(),
    val selectedVideoPath: String? = null,
    val playVideo: Boolean = false
)

data class VideoItem(
    val file: File,
    val name: String,
    val dateCaptured: Long
)