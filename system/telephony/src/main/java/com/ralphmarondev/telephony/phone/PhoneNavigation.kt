package com.ralphmarondev.telephony.phone

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.telephony.phone.presentation.history.HistoryScreenRoot

@Composable
fun PhoneNavigation(
    finishActivity: () -> Unit,
    startDestination: PhoneRoute = PhoneRoute.History,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<PhoneRoute.History> {
            HistoryScreenRoot(
                navigateBack = finishActivity
            )
        }
    }
}