package com.ralphmarondev.core.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ThemeState internal constructor(
    val darkTheme: State<Boolean>,
    private val preferences: AppPreferences,
    private val scope: CoroutineScope
) {
    fun toggleTheme() {
        scope.launch {
            preferences.setSystemInDarkMode(!darkTheme.value)
        }
    }
}

@Composable
fun rememberThemeState(preferences: AppPreferences): ThemeState {
    val darkThemeFlow = preferences.isSystemInDarkMode()
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