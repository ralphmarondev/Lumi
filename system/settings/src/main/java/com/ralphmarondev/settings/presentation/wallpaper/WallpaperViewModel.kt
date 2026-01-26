package com.ralphmarondev.settings.presentation.wallpaper

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WallpaperViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WallpaperState())
    val state = _state.asStateFlow()

    init {
        loadWallpapers()
        loadActiveWallpaper()
        Log.d("WallpaperViewModel", "${state.value.wallpapers}")
    }

    fun onAction(action: WallpaperAction) {
        when (action) {
            is WallpaperAction.ChangeWallpaper -> {
                viewModelScope.launch {
                    repository.setActiveWallpaper(action.value)
                    _state.update {
                        it.copy(activeWallpaper = action.value)
                    }
                }
            }

            WallpaperAction.NavigateBack -> {
                _state.update { it.copy(navigateBack = true) }
            }

            WallpaperAction.ResetNavigation -> {
                _state.update { it.copy(navigateBack = false) }
            }
        }
    }

    private fun loadActiveWallpaper() {
        viewModelScope.launch {
            repository.getActiveWallpaper()
                .collect { w ->
                    _state.update {
                        it.copy(activeWallpaper = w.id)
                    }
                }
        }
    }

    private fun loadWallpapers() {
        viewModelScope.launch {
            val wallpapers = repository.getAllWallpapers()
            _state.update {
                it.copy(wallpapers = wallpapers)
            }
        }
    }
}