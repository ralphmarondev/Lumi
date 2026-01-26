package com.ralphmarondev.launcher.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.core.presentation.shell.LocalLumiShellState
import com.ralphmarondev.core.presentation.shell.LumiShellStyle
import com.ralphmarondev.launcher.presentation.component.AppContainer
import com.ralphmarondev.launcher.presentation.widget.ClockWidget
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LauncherScreenRoot(
    navigateToSettings: () -> Unit,
    navigateToNotes: () -> Unit,
    navigateToClock: () -> Unit,
    navigateToWeather: () -> Unit,
    navigateToCalendar: () -> Unit,
    navigateToCamera: () -> Unit,
    navigateToContacts: () -> Unit,
    navigateToPhotos: () -> Unit,
    navigateToVideos: () -> Unit
) {
    val viewModel: LauncherViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val shellState = LocalLumiShellState.current

    LaunchedEffect(Unit) {
        shellState.setAppearance(LumiShellStyle.WhiteOnTransparent)
    }

    LaunchedEffect(state.navigationTarget) {
        when (state.navigationTarget) {
            NavigationTarget.None -> Unit
            NavigationTarget.Notes -> {
                navigateToNotes()
                viewModel.onAction(LauncherAction.ResetNavigation)
            }

            NavigationTarget.Settings -> {
                navigateToSettings()
                viewModel.onAction(LauncherAction.ResetNavigation)
            }

            NavigationTarget.Clock -> {
                navigateToClock()
                viewModel.onAction(LauncherAction.ResetNavigation)
            }

            NavigationTarget.Weather -> {
                navigateToWeather()
                viewModel.onAction(LauncherAction.ResetNavigation)
            }

            NavigationTarget.Calendar -> {
                navigateToCalendar()
                viewModel.onAction(LauncherAction.ResetNavigation)
            }

            NavigationTarget.Camera -> {
                navigateToCamera()
                viewModel.onAction(LauncherAction.ResetNavigation)
            }

            NavigationTarget.Contacts -> {
                navigateToContacts()
                viewModel.onAction(LauncherAction.ResetNavigation)
            }

            NavigationTarget.Photos -> {
                navigateToPhotos()
                viewModel.onAction(LauncherAction.ResetNavigation)
            }

            NavigationTarget.Videos -> {
                navigateToVideos()
                viewModel.onAction(LauncherAction.ResetNavigation)
            }
        }
    }

    LauncherScreen(state = state)
}

@Composable
private fun LauncherScreen(
    state: LauncherState
) {
    val pagerState = rememberPagerState { state.pageCount }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = state.wallpaper),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 48.dp)
        ) { currentPage ->
            when (currentPage) {
                0 -> FirstPage()
                1 -> SecondPage(state = state)
                else -> FirstPage()
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = Color.Black.copy(alpha = 0.25f))
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pagerState.pageCount) {
                        val active = pagerState.currentPage == it
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(size = if (active) 12.dp else 8.dp)
                                .clip(CircleShape)
                                .background(
                                    color = if (active)
                                        MaterialTheme.colorScheme.primaryContainer
                                    else
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                )
                        )
                    }
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                userScrollEnabled = false
            ) {
                items(items = state.dockApps, key = { it.id }) { app ->
                    AppContainer(info = app)
                }
            }
        }
    }
}

@Composable
private fun WorkspacePage(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
private fun FirstPage() {
    WorkspacePage {
        ClockWidget()
    }
}

@Composable
private fun SecondPage(
    state: LauncherState
) {
    WorkspacePage {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            userScrollEnabled = false
        ) {
            items(items = state.miniApps, key = { it.id }) { app ->
                AppContainer(info = app, showAppName = true)
            }
        }
    }
}