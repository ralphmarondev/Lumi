package com.ralphmarondev.lumi.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.ralphmarondev.lumi.R
import com.ralphmarondev.notes.NoteActivity
import com.ralphmarondev.weather.WeatherActivity

enum class MiniApp {
    Unknown,
    Notes,
    Weather
}

@Suppress("DEPRECATION")
fun Context.launchMiniApp(miniApp: MiniApp) {
    val activityClass = when (miniApp) {
        MiniApp.Notes -> NoteActivity::class.java
        MiniApp.Weather -> WeatherActivity::class.java
        MiniApp.Unknown -> return
    }

    val intent = Intent(this, activityClass)
    startActivity(intent)

    if (this is Activity) {
        this.overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }
}