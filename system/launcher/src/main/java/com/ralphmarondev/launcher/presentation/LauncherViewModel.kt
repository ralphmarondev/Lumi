package com.ralphmarondev.launcher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.domain.model.LumiAppTag
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
                    dockLumiApps = repository.getDockApps(),
                    miniLumiApps = repository.getMiniApps()
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
                    LumiAppTag.Settings.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Settings)
                        }
                    }

                    LumiAppTag.Notes.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Notes)
                        }
                    }

                    LumiAppTag.Clock.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Clock)
                        }
                    }

                    LumiAppTag.Weather.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Weather)
                        }
                    }

                    LumiAppTag.Calendar.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Calendar)
                        }
                    }

                    LumiAppTag.Camera.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Camera)
                        }
                    }

                    LumiAppTag.Photos.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Photos)
                        }
                    }

                    LumiAppTag.Videos.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Videos)
                        }
                    }

                    LumiAppTag.Contacts.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Contacts)
                        }
                    }

                    LumiAppTag.Phone.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Phone)
                        }
                    }

                    LumiAppTag.Calculator.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Calculator)
                        }
                    }

                    LumiAppTag.Community.name -> {
                        _state.update {
                            it.copy(navigationTarget = NavigationTarget.Community)
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