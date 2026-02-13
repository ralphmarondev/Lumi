package com.ralphmarondev.telephony.phone.presentation.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Dialpad
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HistoryScreenRoot(
    navigateBack: () -> Unit
) {
    val viewModel: HistoryViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
        }
    }

    LumiGestureHandler(onBackSwipe = navigateBack) {
        HistoryScreen(
            state = state,
            action = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryScreen(
    state: HistoryState,
    action: (HistoryAction) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Call")
                },
                navigationIcon = {
                    IconButton(onClick = { action(HistoryAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate back"
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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            FloatingActionButton(
                onClick = { action(HistoryAction.ShowDialPad) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .zIndex(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Dialpad,
                    contentDescription = null
                )
            }

            PullToRefreshBox(
                modifier = Modifier.fillMaxSize(),
                isRefreshing = state.isRefreshing,
                onRefresh = { action(HistoryAction.Refresh) }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = if (state.callHistory.isEmpty()) {
                        Arrangement.Center
                    } else {
                        Arrangement.Top
                    }
                ) {
                    item {
                        AnimatedVisibility(state.isLoading && !state.isRefreshing) {
                            Text(
                                text = "Loading call history...",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.secondary
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    item {
                        AnimatedVisibility(visible = state.callHistory.isEmpty() && !state.isLoading) {
                            Text(
                                text = "Your call history is empty!",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.secondary
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    items(state.callHistory) { history ->
                        Text(text = history)
                    }
                }
            }

            if (state.showDialPad) {
                DialPad(
                    state = state, action = action,
                    modifier = Modifier.zIndex(3f)
                )
            }
        }
    }
}

@Composable
fun DialPad(
    state: HistoryState,
    action: (HistoryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable { action(HistoryAction.HideDialPad) }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(state.dialedNumber.isNotBlank()) {
                Text(
                    text = state.dialedNumber,
                    fontSize = 32.sp,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            val keys = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("*", "0", "#")
            )

            keys.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { key ->
                        IconButton(
                            onClick = { action(HistoryAction.DialKey(key)) },
                            modifier = Modifier
                                .size(72.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                        ) {
                            Text(
                                text = key,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { action(HistoryAction.HideDialPad) },
                    modifier = Modifier
                        .size(72.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Dialpad,
                        contentDescription = "Hide dial pad",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = { action(HistoryAction.CallNumber) },
                    modifier = Modifier
                        .size(88.dp)
                        .background(
                            color = Color(0xFF4CAF50),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Call,
                        contentDescription = "Call",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = { action(HistoryAction.DeleteKey) },
                    modifier = Modifier
                        .size(72.dp)
                        .background(
                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Backspace,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}