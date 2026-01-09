package com.ralphmarondev.system.launcher.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.system.launcher.domain.model.MiniApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LauncherViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(LauncherState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val wallpaper = preferences.getSystemLauncherWallpaper().first() + 1
            Log.d("Wallpaper", "Launcher wallpaper: $wallpaper")
            _state.update {
                it.copy(
                    wallpaper = wallpaper,
                    miniApps = listOf(
                        MiniApp(
                            id = 0,
                            name = "Settings",
                            image = com.ralphmarondev.system.R.drawable.setting,
                            onClick = { onAction(LauncherAction.NavigateToSettings) }
                        ),
                        MiniApp(
                            id = 1,
                            name = "Notes",
                            image = com.ralphmarondev.system.R.drawable.notepad,
                            onClick = { onAction(LauncherAction.NavigateToNotes) }
                        ),
                        MiniApp(
                            id = 2,
                            name = "Clock",
                            image = com.ralphmarondev.system.R.drawable.clock,
                            onClick = { onAction(LauncherAction.NavigateToClock) }
                        ),
                        MiniApp(
                            id = 3,
                            name = "Weather",
                            image = com.ralphmarondev.system.R.drawable.weather,
                            onClick = { onAction(LauncherAction.NavigateToWeather) }
                        )
                    )
                )
            }
        }
    }

    fun onAction(action: LauncherAction) {
        when (action) {
            LauncherAction.NavigateToNotes -> {
                _state.update {
                    it.copy(navigationTarget = NavigationTarget.Notes)
                }
            }

            LauncherAction.NavigateToSettings -> {
                _state.update {
                    it.copy(navigationTarget = NavigationTarget.Settings)
                }
            }

            LauncherAction.ResetNavigation -> {
                _state.update {
                    it.copy(navigationTarget = NavigationTarget.None)
                }
            }

            LauncherAction.NavigateToClock -> {
                _state.update {
                    it.copy(navigationTarget = NavigationTarget.Clock)
                }
            }

            LauncherAction.NavigateToWeather -> {
                _state.update {
                    it.copy(navigationTarget = NavigationTarget.Weather)
                }
            }
        }
    }
}