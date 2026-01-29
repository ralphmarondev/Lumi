package com.ralphmarondev.media.photos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ralphmarondev.media.photos.presentation.details.DetailsScreenRoot
import com.ralphmarondev.media.photos.presentation.gallery.GalleryScreenRoot

@Composable
fun PhotosNavigation(
    startDestination: PhotoRoutes = PhotoRoutes.Gallery,
    navController: NavHostController = rememberNavController(),
    exitApp: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<PhotoRoutes.Gallery> {
            GalleryScreenRoot(
                navigateBack = exitApp,
                details = { imagePath ->
                    navController.navigate(PhotoRoutes.Details(imagePath)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<PhotoRoutes.Details> {
            val imagePath = it.toRoute<PhotoRoutes.Details>().imagePath
            DetailsScreenRoot(
                imagePath = imagePath,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}