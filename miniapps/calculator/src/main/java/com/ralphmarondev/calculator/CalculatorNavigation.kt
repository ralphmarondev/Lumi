package com.ralphmarondev.calculator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.calculator.presentation.calculate.CalculateScreenRoot

@Composable
fun CalculatorNavigation(
    navigateBack: () -> Unit,
    startDestination: Routes = Routes.Calculate,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.Calculate> {
            CalculateScreenRoot(navigateBack = navigateBack)
        }
    }
}