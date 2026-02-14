package com.ralphmarondev.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "applications")
data class LumiAppEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val icon: String = "",
    val versionName: String = "",
    val versionCode: Int = 0,
    val isSystemApp: Boolean = false,
    val isDocked: Boolean = false,
    val isInstalled: Boolean = true,
    val order: Int = 0,
    val tag: String = "",
    val resId: Int = 0 // only used on setup
)