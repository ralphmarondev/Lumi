package com.ralphmarondev.weather

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.weather.presentation.home.HomeScreen

@Composable
fun WeatherApp(
    closeApp: () -> Unit,
    navController: NavHostController = rememberNavController(),
    startDestination: Routes = Routes.Home
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.Home> {
            HomeScreen(
                navigateBack = closeApp
            )
        }
    }
}