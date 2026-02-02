package com.ralphmarondev.clock.presentation.alarm

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import org.koin.androidx.compose.koinViewModel
import java.time.LocalTime

@Composable
fun AlarmScreenRoot(
    navigateBack: () -> Unit
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
            action = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlarmScreen(
    state: AlarmState,
    action: (AlarmAction) -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var pickedTime by remember { mutableStateOf(LocalTime.now()) }

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = {
                Log.d("Alarm", "Clicked...")
                showTimePicker = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .zIndex(1f)
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "New Alarm"
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
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
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }

        if (showTimePicker) {
            val timePickerState = remember {
                TimePickerState(
                    initialHour = pickedTime.hour,
                    initialMinute = pickedTime.minute,
                    is24Hour = false
                )
            }

            TimePickerDialog(
                onDismissRequest = { showTimePicker = false },
                title = {
                    Text(text = "Select Alarm Time")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showTimePicker = false
                            Log.d("Alarm", "Picked time: $pickedTime")
                        }
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showTimePicker = false }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                TimePicker(
                    state = timePickerState,
                    modifier = Modifier.padding(16.dp)
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