package com.ralphmarondev.weather.data.network.ktor.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val hourly: HourlyDto
)

@Serializable
data class HourlyDto(
    val time: List<String>,               // ISO 8601 format
    val temperature_2m: List<Double>,
    val precipitation: List<Double>,
    val windspeed_10m: List<Double>,
    val weathercode: List<Int>
)
