package com.ralphmarondev.boot.setup

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.boot.setup.presentation.completed.CompletedScreenRoot
import com.ralphmarondev.boot.setup.presentation.install.InstallScreenRoot
import com.ralphmarondev.boot.setup.presentation.setup.SetupScreenRoot

@Composable
fun SetupNavigation(
    onCompleted: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SetupRoute.Setup
    ) {
        composable<SetupRoute.Setup> {
            SetupScreenRoot(
                installLumi = {
                    navController.navigate(SetupRoute.Installing) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                tryLumi = {}
            )
        }
        composable<SetupRoute.Installing> {
            InstallScreenRoot(
                onComplete = {
                    navController.navigate(SetupRoute.Completed) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<SetupRoute.Completed> {
            CompletedScreenRoot(
                onComplete = onCompleted
            )
        }
    }
}