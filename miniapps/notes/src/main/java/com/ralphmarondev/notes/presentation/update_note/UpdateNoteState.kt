package com.ralphmarondev.notes.presentation.update_note

import com.ralphmarondev.notes.domain.model.Note

data class UpdateNoteState(
    val existingNote: Note = Note(),
    val title: String = "",
    val content: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false
)