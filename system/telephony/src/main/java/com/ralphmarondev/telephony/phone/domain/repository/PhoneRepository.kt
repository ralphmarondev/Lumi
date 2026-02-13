package com.ralphmarondev.telephony.phone.domain.repository

import com.ralphmarondev.core.domain.model.CallHistory

interface PhoneRepository {
    suspend fun getCallHistory(): List<CallHistory>
    suspend fun saveCall(call: CallHistory)
    suspend fun updateCall(call: CallHistory)
}