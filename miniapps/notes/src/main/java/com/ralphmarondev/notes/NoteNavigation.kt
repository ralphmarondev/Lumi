package com.ralphmarondev.notes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ralphmarondev.notes.presentation.new_note.NewNoteScreenRoot
import com.ralphmarondev.notes.presentation.note_list.NoteListScreenRoot
import com.ralphmarondev.notes.presentation.update_note.UpdateNoteScreenRoot

@Composable
fun NoteNavigation(
    closeNoteApp: () -> Unit,
    navController: NavHostController = rememberNavController(),
    startDestination: Routes = Routes.NoteList
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.NoteList> {
            NoteListScreenRoot(
                navigateBack = closeNoteApp,
                navigateToNewNote = {
                    navController.navigate(Routes.NewNote) {
                        launchSingleTop = true
                    }
                },
                updateNote = { noteId ->
                    navController.navigate(Routes.UpdateNote(noteId)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.NewNote> {
            NewNoteScreenRoot(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<Routes.UpdateNote> {
            val noteId = it.toRoute<Routes.UpdateNote>().id

            UpdateNoteScreenRoot(
                noteId = noteId,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}