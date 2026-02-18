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
                            it.copy(launchLumiApp = LumiAppTag.Settings)
                        }
                    }

                    LumiAppTag.Notes.name -> {
                        _state.update {
                            it.copy(launchLumiApp = LumiAppTag.Notes)
                        }
                    }

                    LumiAppTag.Clock.name -> {
                        _state.update {
                            it.copy(launchLumiApp = LumiAppTag.Clock)
                        }
                    }

                    LumiAppTag.Weather.name -> {
                        _state.update {
                            it.copy(launchLumiApp = LumiAppTag.Weather)
                        }
                    }

                    LumiAppTag.Calendar.name -> {
                        _state.update {
                            it.copy(launchLumiApp = LumiAppTag.Calendar)
                        }
                    }

                    LumiAppTag.Camera.name -> {
                        _state.update {
                            it.copy(launchLumiApp = LumiAppTag.Camera)
                        }
                    }

                    LumiAppTag.Photos.name -> {
                        _state.update {
                            it.copy(launchLumiApp = LumiAppTag.Photos)
                        }
                    }

                    LumiAppTag.Videos.name -> {
                        _state.update {
                            it.copy(launchLumiApp = LumiAppTag.Videos)
                        }
                    }

                    LumiAppTag.Contacts.name -> {
                        _state.update {
                            it.copy(launchLumiApp = LumiAppTag.Contacts)
                        }
                    }

                    LumiAppTag.Phone.name -> {
                        _state.update {
                            it.copy(launchLumiApp = LumiAppTag.Phone)
                        }
                    }

                    LumiAppTag.Calculator.name -> {
                        _state.update {
                            it.copy(launchLumiApp = LumiAppTag.Calculator)
                        }
                    }

                    LumiAppTag.Community.name -> {
                        _state.update {
                            it.copy(launchLumiApp = LumiAppTag.Community)
                        }
                    }

                    LumiAppTag.Message.name -> {
                        _state.update { it.copy(launchLumiApp = LumiAppTag.Message) }
                    }

                    LumiAppTag.LumiStore.name -> {
                        _state.update { it.copy(launchLumiApp = LumiAppTag.LumiStore) }
                    }

                    LumiAppTag.Browser.name -> {
                        _state.update { it.copy(launchLumiApp = LumiAppTag.Browser) }
                    }

                    LumiAppTag.Finances.name -> {
                        _state.update { it.copy(launchLumiApp = LumiAppTag.Finances) }
                    }
                }
            }

            LauncherAction.ResetNavigation -> {
                _state.update {
                    it.copy(launchLumiApp = LumiAppTag.Unknown)
                }
            }
        }
    }
}