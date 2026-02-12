package com.ralphmarondev.media.photos.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import com.ralphmarondev.media.R
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreenRoot(
    imagePath: String,
    navigateBack: () -> Unit
) {
    val viewModel: DetailsViewModel = koinViewModel { parametersOf(imagePath) }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
        }
    }

    LumiGestureHandler(onBackSwipe = navigateBack) {
        DetailsScreen(
            state = state,
            action = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreen(
    state: DetailsState,
    action: (DetailsAction) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { action(DetailsAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { action(DetailsAction.ShareImage) }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Share"
                        )
                    }
                    IconButton(onClick = { action(DetailsAction.SetDeleteImageDialogValue(true)) }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box {
            if (state.imagePath.isNotEmpty()) {
                AsyncImage(
                    model = state.imagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No image to display",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }

            if (state.showDeleteDialog) {
                DeletePhotoDialog(
                    onCancel = { action(DetailsAction.SetDeleteImageDialogValue(false)) },
                    onDelete = { action(DetailsAction.DeleteImage) }
                )
            }
        }
    }
}

@Composable
private fun BoxScope.DeletePhotoDialog(
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .zIndex(3f)
            .padding(24.dp)
            .shadow(16.dp, shape = MaterialTheme.shapes.medium)
            .background(
                MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 240.dp)
                .padding(24.dp)
        ) {
            Text(
                text = "Delete photo?",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.delete_photo),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancel) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onDelete) {
                    Text(text = "Delete")
                }
            }
        }
    }
}