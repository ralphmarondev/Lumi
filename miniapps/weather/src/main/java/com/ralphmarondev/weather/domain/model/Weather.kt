package com.ralphmarondev.weather.domain.model

/**
 * High-level weather condition used across the app
 */
enum class WeatherCondition {
    Sunny,
    Cloudy,
    Rainy,
    Windy,
    Stormy,
    Snowy,
    Foggy,
    ClearNight,
    Unknown
}

/**
 * Predefined locations (simple for now)
 */
enum class WeatherLocation {
    HOME,
    WORK,
    SCHOOL
}

/**
 * Hourly weather snapshot
 */
data class HourlyWeather(
    val hour: Int,                     // 0–23
    val temperature: Double,
    val condition: WeatherCondition,
    val windSpeed: Double
)

/**
 * Main weather domain model
 */
data class Weather(
    val location: WeatherLocation,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,                 // %
    val precipitationChance: Int,      // %
    val windSpeed: Double,             // km/h or m/s
    val windDirection: Int,            // degrees
    val condition: WeatherCondition,
    val hourly: List<HourlyWeather>
)