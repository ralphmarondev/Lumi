package com.ralphmarondev.notes.data.repository

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
}