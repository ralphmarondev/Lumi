package com.ralphmarondev.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ralphmarondev.core.data.local.database.entities.ContactEntity

@Dao
interface ContactDao {

    @Insert
    suspend fun create(contactEntity: ContactEntity): Long

    @Update
    suspend fun update(contactEntity: ContactEntity)

    @Query("SELECT * FROM contacts WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ContactEntity?

    @Query("SELECT * FROM contacts")
    suspend fun getAll(): List<ContactEntity>

    @Query("SELECT COUNT(*) FROM contacts")
    suspend fun count(): Int

    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun deleteById(id: Long)
}