package com.ralphmarondev.system.launcher.presentation

import android.app.Activity
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.core.presentation.component.LumiLottie
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import com.ralphmarondev.system.R
import com.ralphmarondev.system.launcher.presentation.component.AppContainer
import com.ralphmarondev.system.launcher.presentation.widget.ClockWidget
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

    val pagerState = rememberPagerState { state.pageCount }
    val wallpaper = when (state.wallpaper) {
        1 -> R.drawable.wallpaper1
        2 -> R.drawable.wallpaper2
        else -> R.drawable.wallpaper1
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = wallpaper),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { currentPage ->
            when (currentPage) {
                0 -> FirstPage()
                1 -> SecondPage()
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
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                userScrollEnabled = false
            ) {
                items(items = state.miniApps, key = { it.id }) { app ->
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
private fun SecondPage() {
    WorkspacePage {
        Text(
            text = "Coming Soon...",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.padding(16.dp)
        )

        LumiLottie(
            animatedResId = R.raw.under_development,
            modifier = Modifier
                .size(200.dp)
        )
    }
}