package com.ralphmarondev.clock.data.repository

import com.ralphmarondev.clock.data.local.database.dao.AlarmDao
import com.ralphmarondev.clock.data.local.database.mapper.toDomain
import com.ralphmarondev.clock.data.local.database.mapper.toEntity
import com.ralphmarondev.clock.domain.model.Alarm
import com.ralphmarondev.clock.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlarmRepositoryImpl(
    private val dao: AlarmDao
) : AlarmRepository {

    override fun getAlarms(): Flow<List<Alarm>> {
        return dao.getAll()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun getById(id: Long): Alarm? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun insert(alarm: Alarm): Long {
        return dao.insert(alarm.toEntity())
    }

    override suspend fun update(alarm: Alarm) {
        dao.update(alarm.toEntity())
    }

    override suspend fun delete(id: Long) {
        dao.deleteById(id)
    }

    override suspend fun setEnabled(id: Long, enabled: Boolean) {
        dao.setEnabled(id, enabled)
    }

    override suspend fun insertDefault(
        hour: Int,
        minute: Int
    ): Long {
        return dao.insert(
            Alarm(
                hour = hour,
                minute = minute,
                label = "",
                isEnabled = true,
                repeatDays = 0,
                ringtoneUri = null,
                vibrate = true
            ).toEntity()
        )
    }
}
