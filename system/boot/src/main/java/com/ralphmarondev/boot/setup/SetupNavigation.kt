package com.ralphmarondev.boot.setup

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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

        }
    }
}