package com.ralphmarondev.weather.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.ralphmarondev.weather.data.local.preferences.WeatherPreferences

val LocalThemeState = compositionLocalOf<ThemeState> {
    error("No ThemeState provided. Make sure to wrap your UI with ThemeProvider.")
}

@Composable
fun ThemeProvider(
    preferences: WeatherPreferences,
    content: @Composable () -> Unit
) {
    val themeState = rememberThemeState(preferences)
    CompositionLocalProvider(LocalThemeState provides themeState) {
        content()
    }
}