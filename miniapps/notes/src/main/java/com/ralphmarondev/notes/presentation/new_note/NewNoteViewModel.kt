package com.ralphmarondev.notes.presentation.new_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.notes.domain.model.Note
import com.ralphmarondev.notes.domain.repository.NoteRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewNoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NewNoteState())
    val state = _state.asStateFlow()

    fun onAction(action: NewNoteAction) {
        when (action) {
            is NewNoteAction.TitleChange -> {
                _state.update {
                    it.copy(title = action.value)
                }
            }

            is NewNoteAction.ContentChange -> {
                _state.update {
                    it.copy(content = action.value)
                }
            }

            NewNoteAction.Save -> saveNote()

            NewNoteAction.NavigateBack -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            NewNoteAction.ResetNavigation -> {
                _state.update {
                    it.copy(navigateBack = false)
                }
            }
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, message = null) }

            val note = Note(
                title = _state.value.title.trim(),
                content = _state.value.content.trim()
            )

            if (note.title.isBlank()) {
                _state.update { it.copy(isLoading = false, message = "Title cannot be blank.") }
                return@launch
            }

            if (note.content.isBlank()) {
                _state.update { it.copy(isLoading = false, message = "Content cannot be blank.") }
                return@launch
            }

            repository.create(note = note)
                .onSuccess {
                    _state.update {
                        delay(2000)
                        it.copy(
                            isLoading = false,
                            message = "Note saved.",
                            title = "",
                            content = ""
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        delay(2000)
                        it.copy(
                            isLoading = false,
                            message = error.message ?: "Failed to save note."
                        )
                    }
                }
        }
    }
}