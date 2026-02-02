package com.ralphmarondev.clock.presentation.stopwatch

sealed interface StopWatchAction {
    data object Start : StopWatchAction
    data object Stop : StopWatchAction
    data object Reset : StopWatchAction
    data object Lap : StopWatchAction
}