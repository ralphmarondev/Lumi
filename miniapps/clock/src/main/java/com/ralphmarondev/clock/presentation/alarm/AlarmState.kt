package com.ralphmarondev.clock.presentation.alarm

import com.ralphmarondev.clock.domain.model.Alarm
import java.time.LocalTime

data class AlarmState(
    val alarms: List<Alarm> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val newAlarm: LocalTime = LocalTime.now()
)