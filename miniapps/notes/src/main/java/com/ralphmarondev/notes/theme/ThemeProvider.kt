package com.ralphmarondev.notes.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.ralphmarondev.notes.data.local.preferences.NoteAppPreferences

val LocalThemeState = compositionLocalOf<ThemeState> {
    error("No ThemeState provided. Make sure to wrap your UI with ThemeProvider.")
}

@Composable
fun ThemeProvider(
    preferences: NoteAppPreferences,
    content: @Composable () -> Unit
) {
    val themeState = rememberThemeState(preferences)
    CompositionLocalProvider(LocalThemeState provides themeState) {
        content()
    }
}