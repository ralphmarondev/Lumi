package com.ralphmarondev.calculator.presentation.calculate

sealed interface CalculateAction {
    data object Compute : CalculateAction
    data object NavigateBack : CalculateAction
    data object ClearNavigation : CalculateAction
    data class NumPress(val key: String) : CalculateAction
}