package com.ralphmarondev.clock

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material.icons.outlined.Timer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ClockViewModel : ViewModel() {

    private val _state = MutableStateFlow(ClockState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                screens = listOf(
                    ClockScreen(
                        icon = Icons.Outlined.Alarm,
                        label = "Alarms",
                        route = Routes.Alarms
                    ),
                    ClockScreen(
                        icon = Icons.Outlined.Timer,
                        label = "Timer",
                        route = Routes.Timers
                    ),
                    ClockScreen(
                        icon = Icons.Outlined.StopCircle,
                        label = "Stopwatch",
                        route = Routes.StopWatch
                    )
                )
            )
        }
    }
}