package com.ralphmarondev.calculator.presentation.calculate

data class CalculateState(
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false,
    val errorMessage: String? = null,
    val showErrorMessage: Boolean = false,
    val equation: String = "",
    val output: String = ""
)