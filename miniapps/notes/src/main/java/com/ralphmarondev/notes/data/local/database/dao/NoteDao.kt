package com.ralphmarondev.notes.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ralphmarondev.notes.data.local.database.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun create(noteEntity: NoteEntity): Long

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getNotes(): Flow<List<NoteEntity>>
}