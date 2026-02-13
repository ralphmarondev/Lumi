package com.ralphmarondev.telephony.phone.data.repository

import com.ralphmarondev.core.data.local.database.dao.CallHistoryDao
import com.ralphmarondev.core.data.local.database.mapper.toDomain
import com.ralphmarondev.core.data.local.database.mapper.toEntity
import com.ralphmarondev.core.domain.model.CallHistory
import com.ralphmarondev.telephony.phone.domain.repository.PhoneRepository

class PhoneRepositoryImpl(
    private val callHistoryDao: CallHistoryDao
) : PhoneRepository {

    override suspend fun getCallHistory(): List<CallHistory> {
        return callHistoryDao.getAll().map { it.toDomain() }
    }

    override suspend fun saveCall(call: CallHistory) {
        callHistoryDao.insert(call.toEntity())
    }

    override suspend fun updateCall(call: CallHistory) {
        callHistoryDao.update(call.toEntity())
    }
}