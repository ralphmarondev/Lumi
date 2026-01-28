package com.ralphmarondev.notes.domain.repository

import com.ralphmarondev.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun create(note: Note): Result<Long>

    fun getList(): Flow<List<Note>>

    suspend fun getNoteById(id: Long): Note?

    suspend fun updateNote(note: Note)
}