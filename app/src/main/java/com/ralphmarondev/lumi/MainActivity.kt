package com.ralphmarondev.lumi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import com.ralphmarondev.core.presentation.theme.LumiTheme
import com.ralphmarondev.core.presentation.theme.ThemeProvider
import com.ralphmarondev.lumi.navigation.AppNavigation
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val preferences: AppPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThemeProvider(preferences = preferences) {
                val themeState = LocalThemeState.current
                LumiTheme(
                    darkTheme = themeState.darkTheme.value
                ) {
                    AppNavigation()
                }
            }
        }
    }
}