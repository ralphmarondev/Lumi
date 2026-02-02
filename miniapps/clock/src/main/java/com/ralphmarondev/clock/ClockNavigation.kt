package com.ralphmarondev.clock

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.clock.presentation.alarm.AlarmScreenRoot
import com.ralphmarondev.clock.presentation.new_alarm.NewAlarmScreenRoot

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
                navigateBack = closeClock,
                newAlarm = {
                    navController.navigate(Routes.NewAlarm) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.NewAlarm>(
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                )
            }
        ) {
            NewAlarmScreenRoot(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}