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
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao

    companion object {
        private const val DATABASE_NAME = "weather_database"

        fun createDatabase(context: Context): WeatherDatabase {
            try {
                val database = Room.databaseBuilder(
                    context = context,
                    klass = WeatherDatabase::class.java,
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