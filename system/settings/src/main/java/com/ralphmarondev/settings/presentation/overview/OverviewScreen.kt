package com.ralphmarondev.settings.presentation.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import com.ralphmarondev.settings.R
import com.ralphmarondev.settings.presentation.component.SettingCard
import org.koin.compose.viewmodel.koinViewModel
import java.io.File

@Composable
fun OverviewScreenRoot(
    navigateBack: () -> Unit,
    account: () -> Unit,
    wallpaper: () -> Unit,
    security: () -> Unit,
    about: () -> Unit
) {
    val viewModel: OverviewViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
            viewModel.onAction(OverviewAction.ResetNavigation)
        }
    }

    LaunchedEffect(state.navigateToAccount) {
        if (state.navigateToAccount) {
            account()
            viewModel.onAction(OverviewAction.ResetNavigation)
        }
    }

    LaunchedEffect(state.navigateToWallpaper) {
        if (state.navigateToWallpaper) {
            wallpaper()
            viewModel.onAction(OverviewAction.ResetNavigation)
        }
    }

    LaunchedEffect(state.navigateToSecurity) {
        if (state.navigateToSecurity) {
            security()
            viewModel.onAction(OverviewAction.ResetNavigation)
        }
    }

    LaunchedEffect(state.navigateToAbout) {
        if (state.navigateToAbout) {
            about()
            viewModel.onAction(OverviewAction.ResetNavigation)
        }
    }
    LumiGestureHandler(onBackSwipe = navigateBack) {
        OverviewScreen(
            state = state,
            action = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OverviewScreen(
    state: OverviewState,
    action: (OverviewAction) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Settings")
                },
                navigationIcon = {
                    IconButton(onClick = { action(OverviewAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { action(OverviewAction.Refresh) },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    AccountSection(state = state)
                    Spacer(modifier = Modifier.height(16.dp))

                    SettingCard(
                        text = "Personal Information",
                        imageVector = Icons.Outlined.AccountCircle,
                        onClick = { action(OverviewAction.NavigateToAccount) },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    SettingCard(
                        text = "Wallpapers and style",
                        imageVector = Icons.Outlined.Palette,
                        onClick = { action(OverviewAction.NavigateToWallpaper) },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    SettingCard(
                        text = "Security",
                        imageVector = Icons.Outlined.Security,
                        onClick = { action(OverviewAction.NavigateToSecurity) },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    SettingCard(
                        text = "About",
                        imageVector = Icons.Outlined.Info,
                        onClick = { action(OverviewAction.NavigateToAbout) },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AccountSection(
    state: OverviewState
) {
    val displayName = state.user.displayName.ifEmpty {
        "LumiOS User"
    }
    val painter = rememberAsyncImagePainter(
        model = state.user.profileImagePath?.takeIf { it.isNotBlank() }?.let { File(it) },
        error = painterResource(R.drawable.ralphmaron),
        placeholder = painterResource(R.drawable.ralphmaron)
    )

    Image(
        painter = painter,
        contentDescription = "User Image",
        modifier = Modifier
            .size(140.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )

    Text(
        text = displayName,
        style = MaterialTheme.typography.headlineMedium.copy(
            color = MaterialTheme.colorScheme.primary
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
    )
    Text(
        text = state.user.username,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.secondary
        ),
        textAlign = TextAlign.Center
    )
}