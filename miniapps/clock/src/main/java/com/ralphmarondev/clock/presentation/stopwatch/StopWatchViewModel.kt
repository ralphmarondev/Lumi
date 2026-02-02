package com.ralphmarondev.clock.presentation.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StopWatchViewModel : ViewModel() {

    private val _state = MutableStateFlow(StopWatchState())
    val state = _state.asStateFlow()

    private var timerJob: Job? = null

    fun onAction(action: StopWatchAction) {
        when (action) {
            StopWatchAction.Start -> start()
            StopWatchAction.Stop -> stop()
            StopWatchAction.Reset -> reset()
            StopWatchAction.Lap -> lap()
        }
    }

    private fun start() {
        if (_state.value.isRunning) return

        val startTime = System.currentTimeMillis() - _state.value.elapsedMillis
        _state.update { it.copy(isRunning = true) }

        timerJob = viewModelScope.launch {
            while (true) {
                val elapsed = System.currentTimeMillis() - startTime
                _state.update { it.copy(elapsedMillis = elapsed) }
                delay(10L)
            }
        }
    }

    private fun stop() {
        timerJob?.cancel()
        _state.update { it.copy(isRunning = false) }
    }

    private fun reset() {
        timerJob?.cancel()
        _state.update { StopWatchState() }
    }

    private fun lap() {
        _state.update {
            it.copy(laps = it.laps + it.elapsedMillis)
        }
    }
}