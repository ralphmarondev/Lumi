package com.ralphmarondev.media.videos.di

import com.ralphmarondev.media.videos.presentation.video_list.VideoListViewModel
import com.ralphmarondev.media.videos.presentation.video_player.VideoPlayerViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val videosModule = module {
    viewModelOf(::VideoListViewModel)
    viewModelOf(::VideoPlayerViewModel)
}