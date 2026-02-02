package com.ralphmarondev.clock.presentation.alarm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import org.koin.androidx.compose.koinViewModel

@Composable
fun AlarmScreenRoot(
    navigateBack: () -> Unit,
    newAlarm: () -> Unit
) {
    val viewModel: AlarmViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onAction(AlarmAction.LoadAlarms)
    }

    LumiGestureHandler(
        onBackSwipe = navigateBack
    ) {
        AlarmScreen(
            state = state,
            action = viewModel::onAction,
            navigateBack = navigateBack,
            newAlarm = newAlarm
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlarmScreen(
    state: AlarmState,
    action: (AlarmAction) -> Unit,
    navigateBack: () -> Unit,
    newAlarm: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Alarms")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = newAlarm,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (state.alarms.isNotEmpty()) {
                Arrangement.Top
            } else {
                Arrangement.Center
            }
        ) {
            item {
                AnimatedVisibility(state.isLoading) {
                    Text(
                        text = "Loading alarms...",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }
            item {
                AnimatedVisibility(state.alarms.isEmpty() && !state.isLoading) {
                    Text(
                        text = "No Alarms yet.",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }
            items(state.alarms) { alarm ->
                AlarmItem(
                    time = alarm.formattedTime,
                    label = alarm.label,
                    enabled = alarm.isEnabled,
                    onToggle = { action(AlarmAction.ToggleAlarm(alarm.id, it)) }
                )
            }
        }
    }
}

@Composable
private fun AlarmItem(
    time: String,
    label: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = time, style = MaterialTheme.typography.headlineMedium)
            if (label.isNotEmpty()) {
                Text(text = label, style = MaterialTheme.typography.bodySmall)
            }
        }

        Switch(
            checked = enabled,
            onCheckedChange = onToggle
        )
    }
}