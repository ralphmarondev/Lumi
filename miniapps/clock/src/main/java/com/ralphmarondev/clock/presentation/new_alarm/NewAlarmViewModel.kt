package com.ralphmarondev.clock.presentation.new_alarm

import androidx.lifecycle.ViewModel
import com.ralphmarondev.clock.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewAlarmViewModel(
    private val repository: AlarmRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NewAlarmState())
    val state = _state.asStateFlow()

    fun onAction(action: NewAlarmAction) {
        when (action) {
            NewAlarmAction.Save -> {
                saveAlarm()
            }
        }
    }

    private fun saveAlarm() {

    }
}