package com.ralphmarondev.clock.presentation.stopwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StopWatchScreenRoot() {
    val viewModel: StopWatchViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    StopWatchScreen(
        state = state,
        action = viewModel::onAction
    )
}

@Composable
private fun StopWatchScreen(
    state: StopWatchState,
    action: (StopWatchAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = state.formattedTime,
                style = androidx.compose.material3.MaterialTheme.typography.displayMedium
            )

            if (!state.isRunning && state.elapsedMillis == 0L) {
                Button(
                    onClick = { action(StopWatchAction.Start) },
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    Text("Start")
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    Button(onClick = { action(StopWatchAction.Stop) }) { Text("Stop") }
                    Button(onClick = { action(StopWatchAction.Reset) }) { Text("Reset") }
                    Button(onClick = { action(StopWatchAction.Lap) }) { Text("Lap") }
                }
            }

            if (state.laps.isNotEmpty()) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    state.laps.forEachIndexed { index, lapTime ->
                        Text(
                            text = "Lap ${index + 1}: " +
                                    "%02d:%02d.%02d".format(
                                        lapTime / 60000 % 60,
                                        lapTime / 1000 % 60,
                                        lapTime / 10 % 100
                                    )
                        )
                    }
                }
            }
        }
    }
}