package com.ralphmarondev.notes.presentation.note_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.notes.data.local.database.dao.NoteDao
import com.ralphmarondev.notes.data.local.database.entities.NoteEntity
import com.ralphmarondev.notes.data.local.database.mapper.toDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val noteDao: NoteDao
) : ViewModel() {

    private val _state = MutableStateFlow(NoteListState())
    val state = _state.asStateFlow()

    init {
        sample()
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
                        navigateToNewNote = false
                    )
                }
            }
        }
    }

    private fun sample() {
        viewModelScope.launch {
            try {
                noteDao.create(noteEntity = NoteEntity(title = "Sample", content = "Content"))
                Log.d("NoteList", "Note saved.")

                Log.d("NoteList", "Reading notes...")
                noteDao.getNotes().collect { notes ->
                    _state.update { current ->
                        current.copy(notes = notes.map { it.toDomain() })
                    }
                }
            } catch (e: Exception) {
                Log.e("NoteList", "Error saving note: ${e.message}")
            }
        }
    }
}