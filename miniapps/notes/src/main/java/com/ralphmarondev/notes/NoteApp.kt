package com.ralphmarondev.notes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.notes.presentation.new_note.NewNoteScreenRoot
import com.ralphmarondev.notes.presentation.note_list.NoteListScreenRoot

@Composable
fun NoteApp(
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
    }
}