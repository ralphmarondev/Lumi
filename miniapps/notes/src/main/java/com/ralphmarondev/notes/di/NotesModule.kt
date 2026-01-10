package com.ralphmarondev.notes.di

import com.ralphmarondev.notes.data.local.database.NoteAppDatabase
import com.ralphmarondev.notes.data.local.preferences.NoteAppPreferences
import com.ralphmarondev.notes.presentation.note_list.NoteListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val notesModule = module {
    single { NoteAppPreferences(context = androidContext().applicationContext) }
    single { NoteAppDatabase.createDatabase(androidContext()) }
    single { get<NoteAppDatabase>().noteDao }

    factory { NoteListViewModel(get()) }
}