package com.ralphmarondev.notes.presentation.note_list

import com.ralphmarondev.notes.domain.model.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val navigateToNewNote: Boolean = false,
    val navigateBack: Boolean = false
)
