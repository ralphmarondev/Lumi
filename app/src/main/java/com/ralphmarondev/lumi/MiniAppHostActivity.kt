package com.ralphmarondev.lumi

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.ralphmarondev.core.presentation.theme.LumiTheme
import com.ralphmarondev.lumi.navigation.MiniAppType
import com.ralphmarondev.notes.NoteApp
import com.ralphmarondev.system.shell.presentation.LumiShell

class MiniAppHostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        enableFullScreen()

        val miniApp = intent.getSerializableExtra(KEY_MINI_APP) as? MiniAppType ?: MiniAppType.Notes

        setContent {
            LumiTheme {
                LumiShell {
                    when (miniApp) {
                        MiniAppType.Notes -> NoteApp(
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

    companion object {
        private const val KEY_MINI_APP = "mini_app"

        fun intent(context: Context, miniApp: MiniAppType): Intent =
            Intent(context, MiniAppHostActivity::class.java).apply {
                putExtra(KEY_MINI_APP, miniApp)
            }
    }
}