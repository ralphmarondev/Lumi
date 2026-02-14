package com.ralphmarondev.clock.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.ralphmarondev.clock.data.local.preferences.ClockPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ThemeState internal constructor(
    val darkTheme: State<Boolean>,
    private val preferences: ClockPreferences,
    private val scope: CoroutineScope
) {
    fun toggleTheme() {
        scope.launch {
            preferences.setDarkMode(!darkTheme.value)
        }
    }
}

@Composable
fun rememberThemeState(preferences: ClockPreferences): ThemeState {
    val darkThemeFlow = preferences.isInDarkMode()
    val darkThemeState = darkThemeFlow.collectAsState(false)
    val scope = rememberCoroutineScope()

    return remember(key1 = preferences, key2 = darkThemeState.value) {
        ThemeState(
            darkTheme = darkThemeState,
            preferences = preferences,
            scope = scope
        )
    }
}