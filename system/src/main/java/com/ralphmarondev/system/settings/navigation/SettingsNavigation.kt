package com.ralphmarondev.system.settings.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import com.ralphmarondev.system.settings.presentation.overview.OverviewScreenRoot
import com.ralphmarondev.system.settings.presentation.security.SecurityScreenRoot
import com.ralphmarondev.system.settings.presentation.wallpaper.WallPaperScreenRoot

@Composable
fun SettingsNavigation(
    navigateBack: () -> Unit
) {
    val navController = rememberNavController()
    val themeState = LocalThemeState.current
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = window?.let {
                WindowCompat.getInsetsController(it, view)
            }
            insetsController?.isAppearanceLightStatusBars = !themeState.darkTheme.value
        }
    }

    NavHost(
        navController = navController,
        startDestination = SettingsRoute.Overview
    ) {
        composable<SettingsRoute.Overview> {
            OverviewScreenRoot(
                navigateBack = navigateBack,
                account = {},
                wallpaper = {
                    navController.navigate(SettingsRoute.Wallpapers) {
                        launchSingleTop = true
                    }
                },
                security = {
                    navController.navigate(SettingsRoute.Security) {
                        launchSingleTop = true
                    }
                },
                about = {}
            )
        }
        composable<SettingsRoute.Wallpapers> {
            WallPaperScreenRoot(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<SettingsRoute.Security> {
            SecurityScreenRoot(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}