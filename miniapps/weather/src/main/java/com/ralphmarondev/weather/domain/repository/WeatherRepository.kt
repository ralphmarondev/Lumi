package com.ralphmarondev.weather.domain.repository

import com.ralphmarondev.weather.domain.model.Weather

interface WeatherRepository {
    suspend fun getTodayWeather(): Weather
}