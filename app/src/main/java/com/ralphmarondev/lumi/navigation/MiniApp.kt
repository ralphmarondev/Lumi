package com.ralphmarondev.lumi.navigation

import android.app.Activity
import android.content.Context
import com.ralphmarondev.lumi.MiniAppHostActivity
import com.ralphmarondev.lumi.R

enum class MiniApp {
    Unknown,
    Notes
}

@Suppress("DEPRECATION")
fun Context.launchMiniApp(miniApp: MiniApp) {
    val intent = MiniAppHostActivity.intent(this, miniApp)
    this.startActivity(intent)
    if (this is Activity) {
        this.overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }
}