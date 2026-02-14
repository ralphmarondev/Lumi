package com.ralphmarondev.core.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ralphmarondev.core.data.local.database.converters.EnumConverters
import com.ralphmarondev.core.data.local.database.dao.LumiAppDao
import com.ralphmarondev.core.data.local.database.dao.CallHistoryDao
import com.ralphmarondev.core.data.local.database.dao.ContactDao
import com.ralphmarondev.core.data.local.database.dao.UserDao
import com.ralphmarondev.core.data.local.database.dao.WallpaperDao
import com.ralphmarondev.core.data.local.database.entities.LumiAppEntity
import com.ralphmarondev.core.data.local.database.entities.CallHistoryEntity
import com.ralphmarondev.core.data.local.database.entities.ContactEntity
import com.ralphmarondev.core.data.local.database.entities.UserEntity
import com.ralphmarondev.core.data.local.database.entities.WallpaperEntity

@Database(
    entities = [
        UserEntity::class,
        WallpaperEntity::class,
        LumiAppEntity::class,
        ContactEntity::class,
        CallHistoryEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(EnumConverters::class)
abstract class LumiDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val wallpaperDao: WallpaperDao
    abstract val lumiAppDao: LumiAppDao
    abstract val contactDao: ContactDao
    abstract val callHistoryDao: CallHistoryDao

    companion object {
        private const val DATABASE_NAME = "lumi_os_database"

        fun createDatabase(context: Context): LumiDatabase {
            try {
                val database = Room.databaseBuilder(
                    context = context,
                    klass = LumiDatabase::class.java,
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