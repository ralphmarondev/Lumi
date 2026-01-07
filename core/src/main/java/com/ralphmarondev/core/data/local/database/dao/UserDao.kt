package com.ralphmarondev.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ralphmarondev.core.data.local.database.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun create(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getByUsername(username: String): UserEntity?
}