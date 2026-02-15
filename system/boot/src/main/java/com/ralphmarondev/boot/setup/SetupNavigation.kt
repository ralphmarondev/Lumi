package com.ralphmarondev.boot.setup

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ralphmarondev.boot.setup.domain.model.SetupResult
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
                installLumi = { result ->
                    navController.navigate(
                        route = SetupRoute.Installing(
                            selectedLanguage = result.selectedLanguage,
                            displayName = result.displayName,
                            username = result.username,
                            password = result.password
                        )
                    ) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                tryLumi = {}
            )
        }
        composable<SetupRoute.Installing> {
            val args = it.toRoute<SetupRoute.Installing>()
            val setupResult = SetupResult(
                selectedLanguage = args.selectedLanguage,
                displayName = args.displayName,
                username = args.username,
                password = args.password
            )
            InstallScreenRoot(
                setupResult = setupResult,
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