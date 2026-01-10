package com.ralphmarondev.notes.data.local.database.mapper

import com.ralphmarondev.notes.data.local.database.entities.NoteEntity
import com.ralphmarondev.notes.domain.model.Note

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content
    )
}

fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content
    )
}