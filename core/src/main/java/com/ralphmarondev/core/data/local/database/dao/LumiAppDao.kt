package com.ralphmarondev.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ralphmarondev.core.data.local.database.entities.LumiAppEntity

@Dao
interface LumiAppDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(lumiAppEntity: LumiAppEntity)

    @Query("SELECT * FROM applications WHERE id = :id AND isInstalled = 1 LIMIT 1")
    suspend fun getById(id: Long): LumiAppEntity?

    @Query("SELECT COUNT(*) FROM applications WHERE isInstalled = 1")
    suspend fun count(): Int

    @Query("SELECT * FROM applications WHERE isInstalled = 1 ORDER BY name ASC")
    suspend fun getInstalledApps(): List<LumiAppEntity>

    @Query("SELECT * FROM applications WHERE isDocked = 1 AND isInstalled  = 1 LIMIT :limit")
    suspend fun getDockApps(limit: Int = 4): List<LumiAppEntity>

    @Query("SELECT * FROM applications ORDER BY name ASC")
    suspend fun getAll(): List<LumiAppEntity>

    @Query("UPDATE applications SET isInstalled = 1 WHERE id = :id")
    suspend fun install(id: Long)

    @Query("UPDATE applications SET isInstalled = 0 WHERE id = :id")
    suspend fun unInstall(id: Long)
}