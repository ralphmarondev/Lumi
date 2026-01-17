package com.ralphmarondev.weather.data.repository

import com.ralphmarondev.weather.domain.model.Weather
import com.ralphmarondev.weather.domain.model.WeatherCondition
import com.ralphmarondev.weather.domain.model.WeatherLocation
import com.ralphmarondev.weather.domain.repository.WeatherRepository

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getTodayWeather(): Weather {
        return Weather(
            location = WeatherLocation.HOME,
            temperature = 98.0,
            feelsLike = 20.0,
            humidity = 30,
            precipitationChance = 40,
            windSpeed = 50.toDouble(),
            windDirection = 70,
            condition = WeatherCondition.ClearNight,
            hourly = emptyList()
        )
    }
}