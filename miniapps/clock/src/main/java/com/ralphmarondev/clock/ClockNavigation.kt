package com.ralphmarondev.clock

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.clock.presentation.alarm.AlarmScreenRoot

@Composable
fun ClockNavigation(
    closeClock: () -> Unit,
    startDestination: Routes = Routes.Alarms,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.Alarms> {
            AlarmScreenRoot(
                navigateBack = closeClock
            )
        }
    }
}