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
    val displayName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val gender: Gender = Gender.NotSet,
    val birthday: String? = null,
    val profileImagePath: String? = null,
    val description: String? = null,
    val role: Role = Role.User,
    val language: String = "English",
    val street: String? = null,
    val city: String? = null,
    val province: String? = null,
    val postalCode: String? = null,
    val country: String? = null,
    val active: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)