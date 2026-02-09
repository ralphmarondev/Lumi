package com.ralphmarondev.media.videos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.media.videos.presentation.video_list.VideoListScreenRoot

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
                navigateBack = finishActivity
            )
        }
        composable<VideoRoutes.VideoPlayer> {

        }
    }
}