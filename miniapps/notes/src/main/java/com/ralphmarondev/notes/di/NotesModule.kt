package com.ralphmarondev.notes.di

import com.ralphmarondev.notes.data.local.database.NoteDatabase
import com.ralphmarondev.notes.data.local.preferences.NotePreferences
import com.ralphmarondev.notes.data.repository.NoteRepositoryImpl
import com.ralphmarondev.notes.domain.repository.NoteRepository
import com.ralphmarondev.notes.presentation.new_note.NewNoteViewModel
import com.ralphmarondev.notes.presentation.note_list.NoteListViewModel
import com.ralphmarondev.notes.presentation.update_note.UpdateNoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val notesModule = module {
    single { NotePreferences(context = androidContext().applicationContext) }
    single { NoteDatabase.createDatabase(androidContext()) }
    single { get<NoteDatabase>().noteDao }
    single<NoteRepository> { NoteRepositoryImpl(get()) }

    viewModelOf(::NoteListViewModel)
    viewModelOf(::NewNoteViewModel)
    viewModelOf(::UpdateNoteViewModel)
}