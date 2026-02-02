package com.ralphmarondev.clock.presentation.new_alarm

sealed interface NewAlarmAction {
    data object Save : NewAlarmAction
}