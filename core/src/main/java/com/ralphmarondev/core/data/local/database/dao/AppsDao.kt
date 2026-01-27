package com.ralphmarondev.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ralphmarondev.core.data.local.database.entities.AppsEntity

@Dao
interface AppsDao {

    @Insert
    suspend fun create(appsEntity: AppsEntity)

    @Query("SELECT * FROM apps WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): AppsEntity?

    @Query("SELECT COUNT(*) FROM apps")
    suspend fun count(): Int

    @Query("SELECT * FROM apps")
    suspend fun getAll(): List<AppsEntity>

    @Query("SELECT * FROM apps WHERE isDocked = 1 LIMIT :limit")
    suspend fun getDockApps(limit: Int = 4): List<AppsEntity>
}