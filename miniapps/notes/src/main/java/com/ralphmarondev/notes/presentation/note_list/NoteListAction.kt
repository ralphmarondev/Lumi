package com.ralphmarondev.notes.presentation.note_list

sealed interface NoteListAction {
    data object NavigateBack : NoteListAction
    data object NavigateToNewNote : NoteListAction
    data object ResetNavigation : NoteListAction
    data class MoveNote(val fromIndex: Int, val toIndex: Int) : NoteListAction
}