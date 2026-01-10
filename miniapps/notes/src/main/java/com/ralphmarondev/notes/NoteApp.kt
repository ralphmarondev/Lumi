package com.ralphmarondev.notes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.notes.data.local.preferences.NoteAppPreferences
import com.ralphmarondev.notes.presentation.new_note.NewNoteScreenRoot
import com.ralphmarondev.notes.presentation.note_list.NoteListScreenRoot
import com.ralphmarondev.notes.theme.LocalThemeState
import com.ralphmarondev.notes.theme.NoteTheme
import com.ralphmarondev.notes.theme.ThemeProvider
import org.koin.compose.koinInject

@Composable
fun NoteApp(
    navigateBack: () -> Unit
) {
    val preferences: NoteAppPreferences = koinInject()
    val navController = rememberNavController()

    ThemeProvider(preferences = preferences) {
        val themeState = LocalThemeState.current

        NoteTheme(
            darkTheme = themeState.darkTheme.value
        ) {
            NavHost(
                navController = navController,
                startDestination = Routes.NoteList
            ) {
                composable<Routes.NoteList> {
                    NoteListScreenRoot(
                        navigateBack = navigateBack,
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
    }
}