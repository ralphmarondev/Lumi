package com.ralphmarondev.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ralphmarondev.core.data.local.database.entities.WallpaperEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpaperDao {

    @Insert
    suspend fun create(wallpaperEntity: WallpaperEntity)

    @Query("SELECT * FROM wallpapers")
    suspend fun getAll(): List<WallpaperEntity>

    @Query("SELECT * FROM wallpapers WHERE id = :id LIMIT 1")
    fun getById(id: Long): Flow<WallpaperEntity?>
}