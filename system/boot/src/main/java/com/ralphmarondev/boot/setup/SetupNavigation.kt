package com.ralphmarondev.boot.setup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Installing")
            }
        }
        composable<SetupRoute.Completed> {

        }
    }
}