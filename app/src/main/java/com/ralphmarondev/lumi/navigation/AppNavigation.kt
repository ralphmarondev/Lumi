package com.ralphmarondev.lumi.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.system.auth.presentation.login.LoginScreenRoot
import com.ralphmarondev.system.launcher.presentation.LauncherScreenRoot
import com.ralphmarondev.system.settings.navigation.SettingsNavigation
import com.ralphmarondev.system.setup.presentation.SetupScreenRoot
import com.ralphmarondev.core.presentation.shell.LumiShell

@Composable
fun AppNavigation(
    startApp: SystemApp = SystemApp.Setup,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    LumiShell {
        NavHost(
            navController = navController,
            startDestination = startApp
        ) {
            composable<SystemApp.Setup>(
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    )
                }
            ) {
                SetupScreenRoot(
                    onCompleted = {
                        navController.navigate(SystemApp.Login) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<SystemApp.Login>(
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    )
                }
            ) {
                LoginScreenRoot(
                    onSuccess = {
                        navController.navigate(SystemApp.Launcher) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<SystemApp.Launcher> {
                LauncherScreenRoot(
                    navigateToSettings = {
                        navController.navigate(SystemApp.Settings) {
                            launchSingleTop = true
                        }
                    },
                    navigateToNotes = {
                        context.launchMiniApp(MiniApp.Notes)
                    },
                    navigateToClock = {
                        Log.d("AppNavigation", "Navigating to clock")
                    },
                    navigateToWeather = {
                        context.launchMiniApp(MiniApp.Weather)
                    }
                )
            }
            composable<SystemApp.Settings>(
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                },
            ) {
                SettingsNavigation(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}