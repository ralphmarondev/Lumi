package com.ralphmarondev.weather.presentation.home

import com.ralphmarondev.weather.domain.model.Weather

data class HomeState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val errorMessage: String? = null,
    val navigateBack: Boolean = false
)