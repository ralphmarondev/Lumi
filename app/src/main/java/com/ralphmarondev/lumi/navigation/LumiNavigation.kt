package com.ralphmarondev.lumi.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.boot.auth.presentation.login.LoginScreenRoot
import com.ralphmarondev.boot.setup.SetupNavigation
import com.ralphmarondev.core.domain.model.LumiAppTag
import com.ralphmarondev.core.presentation.coming_soon.ComingSoonScreen
import com.ralphmarondev.core.presentation.shell.LumiShell
import com.ralphmarondev.launcher.presentation.LauncherScreenRoot
import com.ralphmarondev.settings.navigation.SettingsNavigation

@Composable
fun LumiNavigation(
    startApp: LumiApp = LumiApp.Setup,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    LumiShell {
        NavHost(
            navController = navController,
            startDestination = startApp
        ) {
            composable<LumiApp.Setup>(
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
                SetupNavigation(
                    onCompleted = {
                        navController.navigate(LumiApp.Login) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<LumiApp.Login>(
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
                        navController.navigate(LumiApp.Launcher) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<LumiApp.Launcher> {
                LauncherScreenRoot(
                    navigateToSettings = {
                        navController.navigate(LumiApp.Settings) {
                            launchSingleTop = true
                        }
                    },
                    navigateToNotes = {
                        context.launchLumiApp(LumiAppTag.Notes)
                    },
                    navigateToClock = {
                        context.launchLumiApp(LumiAppTag.Clock)
                    },
                    navigateToWeather = {
                        context.launchLumiApp(LumiAppTag.Weather)
                    },
                    navigateToCalendar = {
                        context.launchLumiApp(LumiAppTag.Calendar)
                    },
                    navigateToCamera = {
                        context.launchLumiApp(LumiAppTag.Camera)
                    },
                    navigateToPhotos = {
                        context.launchLumiApp(LumiAppTag.Photos)
                    },
                    navigateToVideos = {
                        context.launchLumiApp(LumiAppTag.Videos)
                    },
                    navigateToContacts = {
                        context.launchLumiApp(LumiAppTag.Contacts)
                    },
                    navigateToPhone = {
                        context.launchLumiApp(LumiAppTag.Phone)
                    },
                    navigateToCalculator = {
                        context.launchLumiApp(LumiAppTag.Calculator)
                    },
                    navigateToCommunity = {
                        context.launchLumiApp(LumiAppTag.Community)
                    },
                    navigateToMessages = {
                        context.launchLumiApp(LumiAppTag.Message)
                    },
                    navigateToAppStore = {
                        context.launchLumiApp(LumiAppTag.LumiStore)
                    },
                    navigateToFinances = {
                        navController.navigate(LumiApp.ComingSoon) {
                            launchSingleTop = true
                        }
                    },
                    navigateToBrowser = {
                        navController.navigate(LumiApp.ComingSoon) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<LumiApp.Settings>(
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
                SettingsNavigation(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable<LumiApp.ComingSoon>(
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
                ComingSoonScreen(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}