package com.ralphmarondev.clock.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val hour: Int = 0,
    val minute: Int = 0,
    val label: String = "",
    val isEnabled: Boolean = true,
    val repeatDays: Int = 0,
    val ringtoneUri: String? = null,
    val vibrate: Boolean = true
)