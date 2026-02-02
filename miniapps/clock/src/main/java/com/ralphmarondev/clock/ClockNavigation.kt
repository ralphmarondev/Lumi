package com.ralphmarondev.clock

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.clock.presentation.alarm.AlarmScreenRoot
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClockNavigation(
    closeClock: () -> Unit,
    startDestination: Routes = Routes.Alarms,
    navController: NavHostController = rememberNavController()
) {
    val viewModel: ClockViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when {
                            destination?.hasRoute<Routes.Alarms>() == true -> "Alarms"
                            destination?.hasRoute<Routes.Timers>() == true -> "Timers"
                            destination?.hasRoute<Routes.StopWatch>() == true -> "Stop Watch"
                            else -> "Clock"
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                state.screens.forEach { screen ->
                    NavigationBarItem(
                        selected = when (screen.route) {
                            Routes.Alarms -> destination?.hasRoute<Routes.Alarms>() == true
                            Routes.Timers -> destination?.hasRoute<Routes.Timers>() == true
                            Routes.StopWatch -> destination?.hasRoute<Routes.StopWatch>() == true
                        },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.label
                            )
                        },
                        label = {
                            Text(text = screen.label)
                        },
                        onClick = {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                                popUpTo(startDestination) {
                                    saveState = true
                                }
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable<Routes.Alarms> {
                    AlarmScreenRoot(
                        navigateBack = closeClock
                    )
                }
                composable<Routes.Timers> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Timers")
                    }
                }
                composable<Routes.StopWatch> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Stopwatch")
                    }
                }
            }
        }
    }
}