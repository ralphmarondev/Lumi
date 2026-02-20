package com.ralphmarondev.store

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ralphmarondev.store.presentation.app_list.AppListScreenRoot
import com.ralphmarondev.store.presentation.details.DetailScreenRoot

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
            AppListScreenRoot(
                navigateBack = finishActivity,
                navigateToDetails = { id ->
                    navController.navigate(Routes.Detail(id)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.Detail> {
            val id = it.toRoute<Routes.Detail>().id
            DetailScreenRoot(
                id = id,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}