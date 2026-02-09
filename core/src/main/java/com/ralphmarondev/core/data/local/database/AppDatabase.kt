package com.ralphmarondev.core.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ralphmarondev.core.data.local.database.converters.EnumConverters
import com.ralphmarondev.core.data.local.database.dao.AppsDao
import com.ralphmarondev.core.data.local.database.dao.ContactDao
import com.ralphmarondev.core.data.local.database.dao.UserDao
import com.ralphmarondev.core.data.local.database.dao.WallpaperDao
import com.ralphmarondev.core.data.local.database.entities.AppsEntity
import com.ralphmarondev.core.data.local.database.entities.ContactEntity
import com.ralphmarondev.core.data.local.database.entities.UserEntity
import com.ralphmarondev.core.data.local.database.entities.WallpaperEntity

@Database(
    entities = [
        UserEntity::class,
        WallpaperEntity::class,
        AppsEntity::class,
        ContactEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(EnumConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val wallpaperDao: WallpaperDao
    abstract val appsDao: AppsDao
    abstract val contactDao: ContactDao

    companion object {
        private const val DATABASE_NAME = "lumi_os_database"

        fun createDatabase(context: Context): AppDatabase {
            try {
                val database = Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
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