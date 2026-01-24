package com.ralphmarondev.clock.domain.model

import android.annotation.SuppressLint

data class Alarm(
    val id: Long = 0,
    val hour: Int,
    val minute: Int,
    val label: String,
    val isEnabled: Boolean,
    val repeatDays: Int,
    val ringtoneUri: String?,
    val vibrate: Boolean
) {
    val formattedTime: String
        @SuppressLint("DefaultLocale")
        get() = String.format("%02d:%02d", hour, minute)
}
