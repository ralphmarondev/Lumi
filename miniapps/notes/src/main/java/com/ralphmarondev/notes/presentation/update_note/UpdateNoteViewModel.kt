package com.ralphmarondev.notes.presentation.update_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.notes.domain.model.Note
import com.ralphmarondev.notes.domain.repository.NoteRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpdateNoteViewModel(
    private val noteId: Long,
    private val repository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UpdateNoteState())
    val state = _state.asStateFlow()

    init {
        getNoteDetails()
    }

    fun onAction(action: UpdateNoteAction) {
        when (action) {
            is UpdateNoteAction.TitleChange -> {
                _state.update {
                    it.copy(title = action.value)
                }
            }

            is UpdateNoteAction.ContentChange -> {
                _state.update {
                    it.copy(content = action.value)
                }
            }

            UpdateNoteAction.Save -> updateNote()
            UpdateNoteAction.NavigateBack -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            UpdateNoteAction.ResetNavigation -> {
                _state.update {
                    it.copy(navigateBack = false)
                }
            }
        }
    }

    private fun updateNote() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val updatedNote = Note(
                    id = noteId,
                    title = _state.value.title,
                    content = _state.value.content
                )
                repository.updateNote(updatedNote)
                delay(2000)
                _state.update {
                    it.copy(isLoading = false)
                }
            } catch (e: Exception) {
                delay(2000)
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error updating note"
                    )
                }
            }
        }
    }

    private fun getNoteDetails() {
        viewModelScope.launch {
            val existingNote = repository.getNoteById(noteId) ?: Note()

            _state.update {
                it.copy(
                    existingNote = existingNote,
                    title = existingNote.title,
                    content = existingNote.content
                )
            }
        }
    }
}