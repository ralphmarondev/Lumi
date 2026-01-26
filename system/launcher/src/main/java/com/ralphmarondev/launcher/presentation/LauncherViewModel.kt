package com.ralphmarondev.launcher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.launcher.R
import com.ralphmarondev.launcher.domain.model.MiniApp
import com.ralphmarondev.launcher.domain.repository.LauncherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LauncherViewModel(
    private val repository: LauncherRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LauncherState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    dockApps = buildDockApps(),
                    miniApps = buildMiniApps()
                )
            }
            repository.getActiveWallpaper()
                .collect { wallpaper ->
                    _state.update { it.copy(wallpaper = wallpaper.path) }
                }
        }
    }

    private fun buildDockApps(): List<MiniApp> {
        return listOf(
            MiniApp(
                id = 0,
                name = "Settings",
                image = R.drawable.setting,
                onClick = { onAction(LauncherAction.NavigateToSettings) }
            ),
            MiniApp(
                id = 1,
                name = "Notes",
                image = R.drawable.notepad,
                onClick = { onAction(LauncherAction.NavigateToNotes) }
            ),
            MiniApp(
                id = 2,
                name = "Clock",
                image = R.drawable.clock,
                onClick = { onAction(LauncherAction.NavigateToClock) }
            ),
            MiniApp(
                id = 3,
                name = "Weather",
                image = R.drawable.weather,
                onClick = { onAction(LauncherAction.NavigateToWeather) }
            )
        )
    }

    private fun buildMiniApps(): List<MiniApp> {
        return listOf(
            MiniApp(
                id = 10,
                name = "Calendar",
                image = R.drawable.calendar,
                onClick = { onAction(LauncherAction.NavigateToCalendar) }
            ),
            MiniApp(
                id = 11,
                name = "Camera",
                image = R.drawable.camera,
                onClick = { onAction(LauncherAction.NavigateToCamera) }
            ),
            MiniApp(
                id = 12,
                name = "Photos",
                image = R.drawable.photos,
                onClick = { onAction(LauncherAction.NavigateToPhotos) }
            ),
            MiniApp(
                id = 13,
                name = "Videos",
                image = R.drawable.video,
                onClick = { onAction(LauncherAction.NavigateToVideos) }
            ),
            MiniApp(
                id = 14,
                name = "Contacts",
                image = R.drawable.contacts,
                onClick = { onAction(LauncherAction.NavigateToContacts) }
            ),
            MiniApp(
                id = 15,
                name = "Weather",
                image = R.drawable.weather,
                onClick = { onAction(LauncherAction.NavigateToWeather) }
            ),
            MiniApp(
                id = 16,
                name = "Clock",
                image = R.drawable.clock,
                onClick = { onAction(LauncherAction.NavigateToClock) }
            ),
            MiniApp(
                id = 17,
                name = "Settings",
                image = R.drawable.setting,
                onClick = { onAction(LauncherAction.NavigateToSettings) }
            ),
            MiniApp(
                id = 18,
                name = "Notes",
                image = R.drawable.notepad,
                onClick = { onAction(LauncherAction.NavigateToNotes) }
            )
        )
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

            LauncherAction.NavigateToCalendar -> {
                _state.update {
                    it.copy(navigationTarget = NavigationTarget.Calendar)
                }
            }

            LauncherAction.NavigateToCamera -> {
                _state.update {
                    it.copy(navigationTarget = NavigationTarget.Camera)
                }
            }

            LauncherAction.NavigateToContacts -> {
                _state.update {
                    it.copy(navigationTarget = NavigationTarget.Contacts)
                }
            }

            LauncherAction.NavigateToPhotos -> {
                _state.update {
                    it.copy(navigationTarget = NavigationTarget.Photos)
                }
            }

            LauncherAction.NavigateToVideos -> {
                _state.update {
                    it.copy(navigationTarget = NavigationTarget.Videos)
                }
            }
        }
    }
}