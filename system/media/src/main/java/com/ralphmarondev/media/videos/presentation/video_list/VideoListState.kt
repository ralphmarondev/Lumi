package com.ralphmarondev.media.videos.presentation.video_list

import java.io.File

data class VideoListState(
    val videos: List<VideoItem> = emptyList()
)

data class VideoItem(
    val file: File,
    val name: String,
    val dateCaptured: Long
)