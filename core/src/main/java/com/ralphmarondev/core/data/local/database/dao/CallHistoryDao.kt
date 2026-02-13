package com.ralphmarondev.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ralphmarondev.core.data.local.database.entities.CallHistoryEntity

@Dao
interface CallHistoryDao {

    @Query("SELECT * FROM call_history ORDER BY date DESC")
    suspend fun getAll(): List<CallHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(call: CallHistoryEntity)

    @Update
    suspend fun update(call: CallHistoryEntity)

    @Delete
    suspend fun delete(call: CallHistoryEntity)
}