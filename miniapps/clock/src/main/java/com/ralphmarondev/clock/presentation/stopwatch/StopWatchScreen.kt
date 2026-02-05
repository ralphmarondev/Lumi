package com.ralphmarondev.clock.presentation.stopwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ralphmarondev.clock.theme.ClockTheme
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StopWatchScreenRoot(
    navigateBack: () -> Unit
) {
    val viewModel: StopWatchViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LumiGestureHandler(onBackSwipe = navigateBack) {
        StopWatchScreen(
            state = state,
            action = viewModel::onAction
        )
    }
}

@Composable
private fun StopWatchScreen(
    state: StopWatchState,
    action: (StopWatchAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = state.formattedTime,
                fontSize = 76.sp,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (!state.isRunning && state.elapsedMillis == 0L) {
                Button(
                    onClick = { action(StopWatchAction.Start) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Start",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            } else {
                Button(
                    onClick = { action(StopWatchAction.Stop) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Stop",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { action(StopWatchAction.Reset) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Reset",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { action(StopWatchAction.Lap) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Lap",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            if (state.laps.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.laps.forEachIndexed { index, lapTime ->
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Lap ${index + 1}: " +
                                        "%02d:%02d.%02d".format(
                                            lapTime / 60000 % 60,
                                            lapTime / 1000 % 60,
                                            lapTime / 10 % 100
                                        ),
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StopWatchScreenPreview() {
    ClockTheme {
        StopWatchScreen(
            state = StopWatchState(
                isRunning = false,
                laps = listOf(
                    1000000,
                    1000001,
                    1000002,
                )
            ),
            action = {}
        )
    }
}