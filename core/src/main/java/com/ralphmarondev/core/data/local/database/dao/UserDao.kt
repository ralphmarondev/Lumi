package com.ralphmarondev.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ralphmarondev.core.data.local.database.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun create(userEntity: UserEntity): Long

    @Update
    suspend fun update(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): UserEntity?

    @Query("UPDATE users SET password = :password WHERE username = :username")
    suspend fun updatePassword(username: String, password: String)

    @Query("DELETE FROM users WHERE username = :username")
    suspend fun deleteByUsername(username: String): Int

    @Query("SELECT COUNT(*) FROM users")
    suspend fun count(): Int

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): UserEntity?
}