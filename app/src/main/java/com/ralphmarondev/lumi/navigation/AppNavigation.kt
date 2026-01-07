package com.ralphmarondev.lumi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.system.auth.presentation.login.LoginScreenRoot
import com.ralphmarondev.system.setup.presentation.SetupScreenRoot

@Composable
fun AppNavigation(
    startDestination: Routes = Routes.Setup,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.Setup> {
            SetupScreenRoot(
                onCompleted = {
                    navController.navigate(Routes.Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.Login> {
            LoginScreenRoot(
                onSuccess = {
                    navController.navigate(Routes.Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.Launcher> {

        }
        composable<Routes.Settings> {

        }
        composable<Routes.Notes> {

        }
    }
}