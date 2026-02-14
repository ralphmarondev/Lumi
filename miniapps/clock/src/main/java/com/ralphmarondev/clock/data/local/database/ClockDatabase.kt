package com.ralphmarondev.clock.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ralphmarondev.clock.data.local.database.dao.AlarmDao
import com.ralphmarondev.clock.data.local.database.entities.AlarmEntity

@Database(
    entities = [AlarmEntity::class],
    version = 2,
    exportSchema = false
)
abstract class ClockDatabase : RoomDatabase() {
    abstract val alarmDao: AlarmDao

    companion object {
        private const val DATABASE_NAME = "clock_database"

        fun createDatabase(context: Context): ClockDatabase {
            try {
                val database = Room.databaseBuilder(
                    context = context,
                    klass = ClockDatabase::class.java,
                    name = DATABASE_NAME
                ).fallbackToDestructiveMigration(true)
                    .build()
                return database
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }
}