package com.ralphmarondev.lumi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.core.presentation.component.LumiSplash
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import com.ralphmarondev.core.presentation.theme.LumiTheme
import com.ralphmarondev.core.presentation.theme.ThemeProvider
import com.ralphmarondev.lumi.navigation.AppNavigation
import com.ralphmarondev.lumi.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val preferences: AppPreferences by inject()

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
                    var startDestination by remember { mutableStateOf<Routes?>(null) }

                    LaunchedEffect(Unit) {
                        delay(2000)
                        val setupCompleted = preferences.isSystemOnboardingCompleted().first()
                        val authenticated = preferences.isAuthenticated().first()
                        val enabledAuth = preferences.isSystemEnableAuth().first()

                        startDestination = when {
                            setupCompleted && authenticated && !enabledAuth -> Routes.Launcher
                            setupCompleted && authenticated && enabledAuth -> Routes.Login
                            setupCompleted -> Routes.Login
                            else -> Routes.Setup
                        }
                    }

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