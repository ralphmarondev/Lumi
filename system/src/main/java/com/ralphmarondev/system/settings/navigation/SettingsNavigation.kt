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

@Composable
fun SettingsNavigation() {
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
            OverviewScreenRoot()
        }
    }
}