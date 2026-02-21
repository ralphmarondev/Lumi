package com.ralphmarondev.community

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.community.auth.presentation.login.LoginScreenRoot

@Composable
fun CommunityNavigation(
    finishActivity: () -> Unit,
    startDestination: Routes = Routes.Login,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.Login> {
            LoginScreenRoot(
                navigateBack = finishActivity,
                onRegister = {},
                onLoginSuccess = {}
            )
        }
    }
}