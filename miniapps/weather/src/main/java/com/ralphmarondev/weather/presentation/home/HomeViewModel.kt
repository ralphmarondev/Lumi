package com.ralphmarondev.weather.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.weather.domain.model.HourlyWeather
import com.ralphmarondev.weather.domain.model.Weather
import com.ralphmarondev.weather.domain.model.WeatherCondition
import com.ralphmarondev.weather.domain.model.WeatherLocation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadWeather()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.Refresh -> {
                loadWeather(isRefreshing = true)
            }

            HomeAction.NavigateBack -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            HomeAction.ResetNavigation -> {
                _state.update {
                    it.copy(navigateBack = false)
                }
            }
        }
    }

    private fun loadWeather(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                        showErrorMessage = false,
                        isRefreshing = isRefreshing
                    )
                }
                val weather = Weather(
                    location = WeatherLocation.HOME,
                    temperature = 31.5,
                    feelsLike = 34.0,
                    humidity = 78,
                    precipitationChance = 60,
                    windSpeed = 12.0,
                    windDirection = 90,
                    condition = WeatherCondition.Rainy,
                    hourly = listOf(
                        HourlyWeather(9, 30.0, WeatherCondition.Cloudy, 10.0),
                        HourlyWeather(12, 32.0, WeatherCondition.Rainy, 15.0),
                        HourlyWeather(18, 29.0, WeatherCondition.ClearNight, 8.0)
                    )
                )
                if (isRefreshing) {
                    delay(1000)
                }
                _state.update { it.copy(weather = weather) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = e.message ?: "Failed to load weather.",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update {
                    it.copy(isLoading = false, isRefreshing = false)
                }
            }
        }
    }
}