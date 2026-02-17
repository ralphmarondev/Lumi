package com.ralphmarondev.telephony.message

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.core.view.WindowCompat
import com.ralphmarondev.core.data.local.preferences.LumiPreferences
import com.ralphmarondev.core.presentation.shell.LocalLumiShellState
import com.ralphmarondev.core.presentation.shell.LumiShell
import com.ralphmarondev.core.presentation.shell.LumiShellStyle
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import com.ralphmarondev.core.presentation.theme.LumiTheme
import com.ralphmarondev.core.presentation.theme.ThemeProvider
import org.koin.android.ext.android.inject

class MessageActivity : ComponentActivity() {

    private val preferences: LumiPreferences by inject()

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

                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Welcome to lumi message!",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
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