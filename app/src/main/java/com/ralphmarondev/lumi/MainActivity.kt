package com.ralphmarondev.lumi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.core.presentation.component.LumiSplash
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import com.ralphmarondev.core.presentation.theme.LumiTheme
import com.ralphmarondev.core.presentation.theme.ThemeProvider
import com.ralphmarondev.lumi.navigation.AppNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val preferences: AppPreferences by inject()
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility =
            (android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or android.view.View.SYSTEM_UI_FLAG_FULLSCREEN      // hide status bar
                    or android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) // stay hidden on swipe

        setContent {
            ThemeProvider(preferences = preferences) {
                val themeState = LocalThemeState.current
                LumiTheme(
                    darkTheme = themeState.darkTheme.value
                ) {
                    val startDestination by viewModel.startDestination.collectAsState(initial = null)

                    Crossfade(
                        targetState = startDestination,
                        label = "splashToApp"
                    ) { destination ->
                        when (destination) {
                            null -> LumiSplash()
                            else -> AppNavigation(startDestination = destination)
                        }
                    }
                }
            }
        }
    }
}