package com.ralphmarondev.notes.presentation.note_list

import com.ralphmarondev.notes.domain.model.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val navigateToNewNote: Boolean = false,
    val navigateToUpdateNote: Boolean = false,
    val selectedNoteId: Long = 0,
    val navigateBack: Boolean = false
)
