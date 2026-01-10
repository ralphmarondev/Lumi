package com.ralphmarondev.notes.presentation.new_note

sealed interface NewNoteAction {
    data class TitleChange(val value: String) : NewNoteAction
    data class ContentChange(val value: String) : NewNoteAction
    data object Save : NewNoteAction
    data object NavigateBack : NewNoteAction
    data object ResetNavigation : NewNoteAction
}