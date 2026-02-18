package com.ralphmarondev.store.presentation.app_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.core.domain.model.LumiApp
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import org.koin.compose.viewmodel.koinViewModel
import java.io.File

@Composable
fun AppListScreenRoot(
    navigateBack: () -> Unit
) {
    val viewModel: AppListViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
            viewModel.onAction(AppListAction.ClearNavigation)
        }
    }

    LumiGestureHandler(onBackSwipe = navigateBack) {
        AppListScreen(
            state = state,
            action = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppListScreen(
    state: AppListState,
    action: (AppListAction) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Lumi Store")
                },
                navigationIcon = {
                    IconButton(onClick = { action(AppListAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { action(AppListAction.Refresh) },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = if (state.lumiApps.isEmpty()) Arrangement.Center else Arrangement.Top
            ) {
                item {
                    AnimatedVisibility(state.lumiApps.isEmpty()) {
                        Text(
                            text = "Lumi store is empty.",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
                items(state.lumiApps) { app ->
                    AppCard(
                        onClick = { action(AppListAction.AppSelected(app.tag)) },
                        app = app,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }
    }
}

@Composable
private fun AppCard(
    app: LumiApp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val painter = rememberAsyncImagePainter(
                model = app.icon.takeIf { it.isNotBlank() }?.let { File(it) },
                error = painterResource(com.ralphmarondev.core.R.drawable.lumi_logo),
                placeholder = painterResource(com.ralphmarondev.core.R.drawable.lumi_logo)
            )
            Image(
                painter = painter,
                contentDescription = "${app.name} Icon",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier.padding(start = 24.dp)) {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (app.isInstalled) {
                    Text(
                        text = "Installed",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Green
                        )
                    )
                } else {
                    Text(
                        text = "Install",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Green,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}