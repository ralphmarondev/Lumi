package com.ralphmarondev.notes

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import com.ralphmarondev.core.presentation.shell.LocalLumiShellState
import com.ralphmarondev.core.presentation.shell.LumiShell
import com.ralphmarondev.core.presentation.shell.LumiShellStyle
import com.ralphmarondev.notes.data.local.preferences.NoteAppPreferences
import com.ralphmarondev.notes.theme.LocalThemeState
import com.ralphmarondev.notes.theme.NoteTheme
import com.ralphmarondev.notes.theme.ThemeProvider
import org.koin.android.ext.android.inject

class NoteActivity : ComponentActivity() {

    private val preferences: NoteAppPreferences by inject()

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

                    NoteTheme(
                        darkTheme = darkTheme
                    ) {
                        LaunchedEffect(darkTheme) {
                            shellState.setAppearance(
                                if (!darkTheme) LumiShellStyle.WhiteOnTransparent
                                else LumiShellStyle.BlackOnTransparent
                            )
                        }

                        NoteNavigation(closeNoteApp = { finish() })
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
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