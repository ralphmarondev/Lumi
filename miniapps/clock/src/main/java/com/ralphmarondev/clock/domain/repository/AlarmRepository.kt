package com.ralphmarondev.clock.domain.repository

import com.ralphmarondev.clock.domain.model.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAlarms(): Flow<List<Alarm>>

    suspend fun getById(id: Long): Alarm?

    suspend fun insert(alarm: Alarm): Long

    suspend fun update(alarm: Alarm)

    suspend fun delete(id: Long)

    suspend fun setEnabled(id: Long, enabled: Boolean)

    suspend fun insertDefault(
        hour: Int,
        minute: Int
    ): Long
}
