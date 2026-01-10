package com.ralphmarondev.notes

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object NoteList : Routes

    @Serializable
    data object NewNote : Routes
}