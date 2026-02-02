package com.ralphmarondev.clock.presentation.alarm

import com.ralphmarondev.clock.domain.model.Alarm

data class AlarmState(
    val alarms: List<Alarm> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)