package com.ralphmarondev.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ralphmarondev.core.domain.model.Gender
import com.ralphmarondev.core.domain.model.Role

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["username"], unique = true),
        Index(value = ["email"], unique = true),
        Index(value = ["phoneNumber"], unique = true)
    ]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String = "",
    val password: String = "",
    val displayName: String = "",
    val email: String? = null,
    val phoneNumber: String? = null,
    val gender: Gender = Gender.NotSet,
    val birthday: String = "",
    val profileImagePath: String? = null,
    val description: String = "",
    val language: String = "English",
    val street: String = "",
    val city: String = "",
    val province: String = "",
    val postalCode: String = "",
    val country: String = "",
    val role: Role = Role.User,
    val active: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)