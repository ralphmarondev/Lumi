package com.ralphmarondev.weather.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ralphmarondev.weather.data.local.database.dao.WeatherDao
import com.ralphmarondev.weather.data.local.database.entities.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao

    companion object {
        private const val DATABASE_NAME = "weather_database"

        fun createDatabase(context: Context): WeatherAppDatabase {
            try {
                val database = Room.databaseBuilder(
                    context = context,
                    klass = WeatherAppDatabase::class.java,
                    name = DATABASE_NAME
                ).build()
                return database
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }
}