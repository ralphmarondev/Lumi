package com.ralphmarondev.clock.presentation.stopwatch

import java.util.concurrent.TimeUnit

data class StopWatchState(
    val isRunning: Boolean = false,
    val elapsedMillis: Long = 0L,
    val laps: List<Long> = emptyList()
) {
    val formattedTime: String
        get() {
            val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedMillis) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedMillis) % 60
            val hundredths = (elapsedMillis / 10) % 10
            return "%02d:%02d.%02d".format(minutes, seconds, hundredths)
        }
}