package com.ralphmarondev.launcher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.domain.model.ApplicationTag
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
                    dockApps = repository.getDockApps(),
                    miniApps = repository.getMiniApps()
                )
            }
            repository.getActiveWallpaper()
                .collect { wallpaper ->
                    _state.update { it.copy(wallpaper = wallpaper.path) }
                }
        }
    }

    fun onAction(action: LauncherAction) {
        when (action) {
            is LauncherAction.OnAppClick -> {
                when (action.tag) {
                    ApplicationTag.Settings.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Settings)
                        }
                    }

                    ApplicationTag.Notes.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Notes)
                        }
                    }

                    ApplicationTag.Clock.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Clock)
                        }
                    }

                    ApplicationTag.Weather.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Weather)
                        }
                    }

                    ApplicationTag.Calendar.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Calendar)
                        }
                    }

                    ApplicationTag.Camera.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Camera)
                        }
                    }

                    ApplicationTag.Photos.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Photos)
                        }
                    }

                    ApplicationTag.Videos.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Videos)
                        }
                    }

                    ApplicationTag.Contacts.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Contacts)
                        }
                    }
                }
            }

            LauncherAction.ResetNavigation -> {
                _state.update {
                    it.copy(navigationTarget = NavigationTarget.None)
                }
            }
        }
    }
}