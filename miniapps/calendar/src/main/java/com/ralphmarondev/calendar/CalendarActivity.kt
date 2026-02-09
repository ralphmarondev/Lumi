package com.ralphmarondev.calendar

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.core.presentation.shell.LocalLumiShellState
import com.ralphmarondev.core.presentation.shell.LumiShell
import com.ralphmarondev.core.presentation.shell.LumiShellStyle
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import com.ralphmarondev.core.presentation.theme.LumiTheme
import com.ralphmarondev.core.presentation.theme.ThemeProvider
import org.koin.android.ext.android.inject

class CalendarActivity : ComponentActivity() {

    private val preferences: AppPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        enableFullScreen()
        setContent {
            LumiShell {
                ThemeProvider(preferences = preferences) {
                    val themeState = LocalThemeState.current
                    val darkTheme = themeState.darkTheme.value
                    val shellState = LocalLumiShellState.current

                    LumiTheme(darkTheme = darkTheme) {
                        LaunchedEffect(darkTheme) {
                            shellState.setAppearance(
                                if (!darkTheme) LumiShellStyle.WhiteOnTransparent
                                else LumiShellStyle.BlackOnTransparent
                            )
                        }

                        CalendarNavigation(
                            navigateBack = { finish() }
                        )
                    }
                }

            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) enableFullScreen()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        enableFullScreen()
    }

    @Suppress("DEPRECATION")
    override fun finish() {
        super.finish()
        overridePendingTransition(
            com.ralphmarondev.core.R.anim.slide_in_left,
            com.ralphmarondev.core.R.anim.slide_out_right
        )
    }

    private fun enableFullScreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility =
            (android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or android.view.View.SYSTEM_UI_FLAG_FULLSCREEN      // hide status bar
                    or android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) // stay hidden on swipe
        actionBar?.hide()
    }
}