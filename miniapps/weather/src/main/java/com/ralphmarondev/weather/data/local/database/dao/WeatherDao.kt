package com.ralphmarondev.weather.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ralphmarondev.weather.data.local.database.entities.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert
    suspend fun create(weatherEntity: WeatherEntity): Long

    @Query("SELECT * FROM weather ORDER BY id DESC")
    fun getWeatherList(): Flow<List<WeatherEntity>>
}