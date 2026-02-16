package com.ralphmarondev.calculator.presentation.calculate

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculateViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculateState())
    val state = _state.asStateFlow()

    fun onAction(action: CalculateAction) {
        when (action) {
            CalculateAction.NavigateBack -> {
                _state.update { it.copy(navigateBack = true) }
            }

            CalculateAction.ClearNavigation -> {
                _state.update { it.copy(navigateBack = false) }
            }

            is CalculateAction.NumPress -> {
                _state.update {
                    it.copy(
                        equation = it.equation + action.key,
                        output = "IDK + C"
                    )
                }
            }

            CalculateAction.Compute -> {

            }
        }
    }
}