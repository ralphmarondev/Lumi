package com.ralphmarondev.clock.presentation.alarm

sealed interface AlarmAction {

    data object LoadAlarms : AlarmAction

    data class ToggleAlarm(
        val id: Long,
        val enabled: Boolean
    ) : AlarmAction

    data class DeleteAlarm(
        val id: Long
    ) : AlarmAction

    data class AddAlarm(
        val hour: Int,
        val minute: Int
    ) : AlarmAction
}
