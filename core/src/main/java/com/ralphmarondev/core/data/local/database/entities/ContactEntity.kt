package com.ralphmarondev.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val contactImagePath: String? = null,
    val createDate: Long = System.currentTimeMillis(),
    val lastUpdateDate: Long? = null
)