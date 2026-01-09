package com.ralphmarondev.system.settings.presentation.wallpaper

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WallpaperViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(WallpaperState())
    val state = _state.asStateFlow()

    fun onAction(action: WallpaperAction) {
        when (action) {
            is WallpaperAction.ChangeWallpaper -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(wallpaper = action.value)
                    }
                    Log.d("Wallpaper", "Index: ${_state.value.wallpaper}")
                    preferences.setSystemLauncherWallpaper(_state.value.wallpaper)
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
}