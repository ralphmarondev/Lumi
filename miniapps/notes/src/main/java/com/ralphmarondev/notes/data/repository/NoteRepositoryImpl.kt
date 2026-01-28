package com.ralphmarondev.notes.data.repository

import android.util.Log
import com.ralphmarondev.notes.data.local.database.dao.NoteDao
import com.ralphmarondev.notes.data.local.database.mapper.toDomain
import com.ralphmarondev.notes.data.local.database.mapper.toEntity
import com.ralphmarondev.notes.domain.model.Note
import com.ralphmarondev.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {
    override suspend fun create(note: Note): Result<Long> {
        return runCatching {
            noteDao.create(note.toEntity())
        }
    }

    override fun getList(): Flow<List<Note>> {
        return noteDao.getNotes()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return try {
            noteDao.getNoteById(id)?.toDomain()
        } catch (e: Exception) {
            Log.e("Note", "Error getNoteById(): ${e.message}")
            null
        }
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNoteById(
            id = note.id,
            title = note.title,
            content = note.content
        )
    }
}