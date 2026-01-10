package com.ralphmarondev.notes.di

import com.ralphmarondev.notes.data.local.database.NoteAppDatabase
import com.ralphmarondev.notes.data.local.preferences.NoteAppPreferences
import com.ralphmarondev.notes.data.repository.NoteRepositoryImpl
import com.ralphmarondev.notes.domain.repository.NoteRepository
import com.ralphmarondev.notes.presentation.new_note.NewNoteViewModel
import com.ralphmarondev.notes.presentation.note_list.NoteListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val notesModule = module {
    single { NoteAppPreferences(context = androidContext().applicationContext) }
    single { NoteAppDatabase.createDatabase(androidContext()) }
    single { get<NoteAppDatabase>().noteDao }
    single<NoteRepository> { NoteRepositoryImpl(get()) }

    viewModelOf(::NoteListViewModel)
    viewModelOf(::NewNoteViewModel)
}