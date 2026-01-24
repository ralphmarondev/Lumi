package com.ralphmarondev.clock.presentation.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.clock.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlarmViewModel(
    private val repository: AlarmRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AlarmState())
    val state: StateFlow<AlarmState> = _state.asStateFlow()

    fun onAction(action: AlarmAction) {
        when (action) {
            AlarmAction.LoadAlarms -> loadAlarms()

            is AlarmAction.ToggleAlarm ->
                toggleAlarm(action.id, action.enabled)

            is AlarmAction.DeleteAlarm ->
                deleteAlarm(action.id)

            is AlarmAction.AddAlarm ->
                addAlarm(action.hour, action.minute)
        }
    }

    private fun loadAlarms() {
        viewModelScope.launch {
            repository.getAlarms().collect { alarms ->
                _state.update {
                    it.copy(
                        alarms = alarms,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun toggleAlarm(id: Long, enabled: Boolean) {
        viewModelScope.launch {
            repository.setEnabled(id, enabled)
        }
    }

    private fun deleteAlarm(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    private fun addAlarm(hour: Int, minute: Int) {
        viewModelScope.launch {
            repository.insertDefault(hour, minute)
        }
    }
}
