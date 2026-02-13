package com.ralphmarondev.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ralphmarondev.core.domain.model.CallType

@Entity(tableName = "call_history")
data class CallHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val owner: String = "",
    val name: String? = null,
    val number: String = "",
    val type: String = CallType.Outgoing.name,
    val date: Long = System.currentTimeMillis(),
    val duration: Long = 0
)