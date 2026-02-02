package com.ralphmarondev.clock.presentation.alarm

import java.time.LocalTime

sealed interface AlarmAction {

    data object LoadAlarms : AlarmAction

    data class ToggleAlarm(val id: Long, val enabled: Boolean) : AlarmAction

    data class DeleteAlarm(val id: Long) : AlarmAction

    data class NewAlarm(val time: LocalTime) : AlarmAction
}