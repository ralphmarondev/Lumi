package com.ralphmarondev.store

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.store.presentation.app_list.AppListScreenRoot

@Composable
fun StoreNavigation(
    finishActivity: () -> Unit,
    startDestination: Routes = Routes.AppList,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.AppList> {
            AppListScreenRoot(navigateBack = finishActivity)
        }
    }
}