package com.ralphmarondev.notes.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ralphmarondev.core.data.local.database.AppDatabase
import com.ralphmarondev.notes.data.local.database.dao.NoteDao
import com.ralphmarondev.notes.data.local.database.entities.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NoteAppDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        private const val DATABASE_NAME = "note_database"

        fun createDatabase(context: Context): NoteAppDatabase {
            try {
                val database = Room.databaseBuilder(
                    context = context,
                    klass = NoteAppDatabase::class.java,
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