package com.ralphmarondev.notes.presentation.new_note

data class NewNoteState(
    val title: String = "",
    val content: String = "",
    val message: String? = null,
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false
)