package com.ralphmarondev.lumi.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.ralphmarondev.calculator.CalculatorActivity
import com.ralphmarondev.calendar.CalendarActivity
import com.ralphmarondev.clock.ClockActivity
import com.ralphmarondev.core.domain.model.LumiAppTag
import com.ralphmarondev.lumi.R
import com.ralphmarondev.media.camera.CameraActivity
import com.ralphmarondev.media.photos.PhotosActivity
import com.ralphmarondev.media.videos.VideosActivity
import com.ralphmarondev.notes.NoteActivity
import com.ralphmarondev.telephony.contacts.ContactsActivity
import com.ralphmarondev.telephony.phone.PhoneActivity
import com.ralphmarondev.weather.WeatherActivity
import kotlinx.serialization.Serializable

@Serializable
sealed interface LumiApp {

    @Serializable
    data object Setup : LumiApp

    @Serializable
    data object Login : LumiApp

    @Serializable
    data object Launcher : LumiApp

    @Serializable
    data object Settings : LumiApp

    @Serializable
    data object ComingSoon : LumiApp
}

@Suppress("DEPRECATION")
fun Context.launchLumiApp(appTag: LumiAppTag) {
    val activityClass = when (appTag) {
        LumiAppTag.Settings -> return
        LumiAppTag.Notes -> NoteActivity::class.java
        LumiAppTag.Weather -> WeatherActivity::class.java
        LumiAppTag.Clock -> ClockActivity::class.java
        LumiAppTag.Camera -> CameraActivity::class.java
        LumiAppTag.Photos -> PhotosActivity::class.java
        LumiAppTag.Videos -> VideosActivity::class.java
        LumiAppTag.Calendar -> CalendarActivity::class.java
        LumiAppTag.Contacts -> ContactsActivity::class.java
        LumiAppTag.Phone -> PhoneActivity::class.java
        LumiAppTag.Community -> return
        LumiAppTag.Calculator -> CalculatorActivity::class.java
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