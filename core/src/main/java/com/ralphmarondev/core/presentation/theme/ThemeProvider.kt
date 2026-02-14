package com.ralphmarondev.core.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.ralphmarondev.core.data.local.preferences.LumiPreferences

val LocalThemeState = compositionLocalOf<ThemeState> {
    error("No ThemeState provided. Make sure to wrap your UI with ThemeProvider.")
}

@Composable
fun ThemeProvider(
    preferences: LumiPreferences,
    content: @Composable () -> Unit
) {
    val themeState = rememberThemeState(preferences)
    CompositionLocalProvider(LocalThemeState provides themeState) {
        content()
    }
}