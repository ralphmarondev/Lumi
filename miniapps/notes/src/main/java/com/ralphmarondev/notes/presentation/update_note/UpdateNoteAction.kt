package com.ralphmarondev.notes.presentation.update_note

sealed interface UpdateNoteAction {
    data class TitleChange(val value: String) : UpdateNoteAction
    data class ContentChange(val value: String) : UpdateNoteAction
    data object Save : UpdateNoteAction
    data object NavigateBack : UpdateNoteAction
    data object ResetNavigation : UpdateNoteAction
}