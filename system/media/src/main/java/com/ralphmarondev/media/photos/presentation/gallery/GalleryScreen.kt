package com.ralphmarondev.media.photos.presentation.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import com.ralphmarondev.core.presentation.shell.LocalLumiShellState
import com.ralphmarondev.core.presentation.shell.LumiShellStyle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GalleryScreenRoot(
    navigateBack: () -> Unit,
    details: (String) -> Unit
) {
    val viewModel: GalleryViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val shellState = LocalLumiShellState.current

    LaunchedEffect(Unit) {
        shellState.setAppearance(LumiShellStyle.WhiteOnTransparent)
    }

    LaunchedEffect(state.navigateToDetails) {
        if (state.navigateToDetails) {
            details(state.selectedImagePath)
            viewModel.onAction(GalleryAction.ResetNavigation)
        }
    }

    LumiGestureHandler(onBackSwipe = navigateBack) {
        GalleryScreen(
            state = state,
            action = viewModel::onAction,
            navigateBack = navigateBack
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    state: GalleryState,
    action: (GalleryAction) -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Gallery")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        if (state.images.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No images found", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.images) { path ->
                    AsyncImage(
                        model = path,
                        contentDescription = null,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clickable { action(GalleryAction.SelectImage(path)) },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
