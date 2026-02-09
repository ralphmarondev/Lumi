package com.ralphmarondev.media.videos.presentation.video_list

sealed interface VideoListAction {
    object LoadVideos : VideoListAction
    data class PlayVideo(val video: VideoItem) : VideoListAction
}