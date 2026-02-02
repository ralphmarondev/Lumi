package com.ralphmarondev.calendar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.calendar.presentation.overview.OverviewScreenRoot

@Composable
fun CalendarNavigation(
    navigateBack: () -> Unit,
    startDestination: Routes = Routes.Overview,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.Overview> {
            OverviewScreenRoot(
                navigateBack = navigateBack
            )
        }
    }
}