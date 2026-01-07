package com.ralphmarondev.system.launcher.presentation

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import com.ralphmarondev.system.launcher.presentation.component.AppContainer
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LauncherScreenRoot(
    navigateToSettings: () -> Unit,
    navigateToNotes: () -> Unit
) {
    val viewModel: LauncherViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigationTarget) {
        when (state.navigationTarget) {
            NavigationTarget.None -> {}
            NavigationTarget.Notes -> {
                navigateToNotes()
                viewModel.onAction(LauncherAction.ResetNavigation)
            }

            NavigationTarget.Settings -> {
                navigateToSettings()
                viewModel.onAction(LauncherAction.ResetNavigation)
            }

            NavigationTarget.Clock -> {}
            NavigationTarget.Weather -> {}
        }
    }

    LauncherScreen(
        state = state,
        action = viewModel::onAction
    )
}

@Composable
private fun LauncherScreen(
    state: LauncherState,
    action: (LauncherAction) -> Unit
) {
    val themeState = LocalThemeState.current
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = window?.let {
                WindowCompat.getInsetsController(it, view)
            }
            insetsController?.isAppearanceLightStatusBars = themeState.darkTheme.value
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = com.ralphmarondev.system.R.drawable.wallpaper2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(1f))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    userScrollEnabled = false
                ) {
                    items(items = state.miniApps, key = { it.id }) { app ->
                        AppContainer(info = app)
                    }
                }
            }
        }
    }
}