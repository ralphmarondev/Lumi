package com.ralphmarondev.notes.presentation.note_list

sealed interface NoteListAction {
    data object NavigateBack : NoteListAction
    data object NavigateToNewNote : NoteListAction
    data class UpdateNote(val id: Long) : NoteListAction
    data object ResetNavigation : NoteListAction
    data class MoveNote(val fromIndex: Int, val toIndex: Int) : NoteListAction
}