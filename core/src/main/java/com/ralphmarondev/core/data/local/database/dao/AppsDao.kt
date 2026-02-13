package com.ralphmarondev.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ralphmarondev.core.data.local.database.entities.AppsEntity

@Dao
interface AppsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(appsEntity: AppsEntity)

    @Query("SELECT * FROM apps WHERE id = :id AND isInstalled = 1 LIMIT 1")
    suspend fun getById(id: Long): AppsEntity?

    @Query("SELECT COUNT(*) FROM apps WHERE isInstalled = 1")
    suspend fun count(): Int

    @Query("SELECT * FROM apps WHERE isInstalled = 1 ORDER BY name ASC")
    suspend fun getAll(): List<AppsEntity>

    @Query("SELECT * FROM apps WHERE isDocked = 1 AND isInstalled  = 1 LIMIT :limit")
    suspend fun getDockApps(limit: Int = 4): List<AppsEntity>
}