package com.ralphmarondev.media.videos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ralphmarondev.media.videos.presentation.video_list.VideoListScreenRoot
import com.ralphmarondev.media.videos.presentation.video_player.VideoPlayerScreenRoot

@Composable
fun VideoNavigation(
    startDestination: VideoRoutes = VideoRoutes.VideoList,
    navController: NavHostController = rememberNavController(),
    finishActivity: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<VideoRoutes.VideoList> {
            VideoListScreenRoot(
                navigateBack = finishActivity,
                playVideo = { videoPath ->
                    navController.navigate(VideoRoutes.VideoPlayer(videoPath))
                }
            )
        }
        composable<VideoRoutes.VideoPlayer> {
            val videoPath = it.toRoute<VideoRoutes.VideoPlayer>().videoPath
            VideoPlayerScreenRoot(
                videoPath = videoPath,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}