package com.ralphmarondev.notes.presentation.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NoteListState())
    val state = _state.asStateFlow()

    init {
        getNotes()
    }

    fun onAction(action: NoteListAction) {
        when (action) {
            NoteListAction.NavigateBack -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            NoteListAction.NavigateToNewNote -> {
                _state.update {
                    it.copy(navigateToNewNote = true)
                }
            }

            NoteListAction.ResetNavigation -> {
                _state.update {
                    it.copy(
                        navigateBack = false,
                        navigateToNewNote = false,
                        navigateToUpdateNote = false
                    )
                }
            }

            is NoteListAction.MoveNote -> {
                val notes = _state.value.notes.toMutableList()
                if (action.fromIndex in notes.indices && action.toIndex in notes.indices) {
                    val note = notes.removeAt(action.fromIndex)
                    notes.add(action.toIndex, note)
                    _state.update { it.copy(notes = notes) }
                }
            }

            is NoteListAction.UpdateNote -> {
                _state.update {
                    it.copy(navigateToUpdateNote = true, selectedNoteId = action.id)
                }
            }
        }
    }

    private fun getNotes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            repository.getList()
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "Unknown error"
                        )
                    }
                }
                .collect { notes ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            notes = notes
                        )
                    }
                }
        }
    }
}