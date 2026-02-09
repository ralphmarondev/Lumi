package com.ralphmarondev.lumi.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.ralphmarondev.calendar.CalendarActivity
import com.ralphmarondev.clock.ClockActivity
import com.ralphmarondev.lumi.R
import com.ralphmarondev.media.camera.CameraActivity
import com.ralphmarondev.media.photos.PhotosActivity
import com.ralphmarondev.media.videos.VideosActivity
import com.ralphmarondev.notes.NoteActivity
import com.ralphmarondev.telephony.contacts.ContactsActivity
import com.ralphmarondev.weather.WeatherActivity

enum class MiniApp {
    Unknown,
    Notes,
    Weather,
    Clock,
    Camera,
    Photos,
    Videos,
    Calendar,
    Contacts
}

@Suppress("DEPRECATION")
fun Context.launchMiniApp(miniApp: MiniApp) {
    val activityClass = when (miniApp) {
        MiniApp.Notes -> NoteActivity::class.java
        MiniApp.Weather -> WeatherActivity::class.java
        MiniApp.Clock -> ClockActivity::class.java
        MiniApp.Camera -> CameraActivity::class.java
        MiniApp.Photos -> PhotosActivity::class.java
        MiniApp.Videos -> VideosActivity::class.java
        MiniApp.Calendar -> CalendarActivity::class.java
        MiniApp.Contacts -> ContactsActivity::class.java
        MiniApp.Unknown -> return
    }

    val intent = Intent(this, activityClass).apply {
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    startActivity(intent)

    if (this is Activity) {
        this.overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }
}