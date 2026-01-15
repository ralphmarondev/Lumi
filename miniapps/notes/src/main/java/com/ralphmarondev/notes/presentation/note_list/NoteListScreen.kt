package com.ralphmarondev.notes.presentation.note_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import com.ralphmarondev.notes.domain.model.Note
import com.ralphmarondev.notes.theme.LocalThemeState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NoteListScreenRoot(
    navigateBack: () -> Unit,
    navigateToNewNote: () -> Unit
) {
    val viewModel: NoteListViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
            viewModel.onAction(NoteListAction.ResetNavigation)
        }
    }

    LaunchedEffect(state.navigateToNewNote) {
        if (state.navigateToNewNote) {
            navigateToNewNote()
            viewModel.onAction(NoteListAction.ResetNavigation)
        }
    }

    LumiGestureHandler(
        onBackSwipe = navigateBack
    ) {
        NoteListScreen(
            state = state,
            action = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteListScreen(
    state: NoteListState,
    action: (NoteListAction) -> Unit
) {
    val themeState = LocalThemeState.current
    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        if (state.errorMessage != null) {
            snackbarState.showSnackbar(message = state.errorMessage)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Notes")
                },
                navigationIcon = {
                    IconButton(onClick = { action(NoteListAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = themeState::toggleTheme) {
                        Icon(
                            imageVector = if (themeState.darkTheme.value) {
                                Icons.Outlined.LightMode
                            } else {
                                Icons.Outlined.DarkMode
                            },
                            contentDescription = if (themeState.darkTheme.value) {
                                "Switch to light mode"
                            } else {
                                "Switch to dark mode"
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { action(NoteListAction.NavigateToNewNote) },
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "New note"
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarState) }
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp

        ) {
            items(items = state.notes, key = { it.id }) { note ->
                NoteCard(
                    note = note,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
private fun NoteCard(
    onClick: () -> Unit,
    note: Note,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}