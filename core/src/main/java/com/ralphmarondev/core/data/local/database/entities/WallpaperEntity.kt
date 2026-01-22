package com.ralphmarondev.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallpapers")
data class WallpaperEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val path: String = "",
    val owner: String = "",
    val createDate: Long = System.currentTimeMillis()
)