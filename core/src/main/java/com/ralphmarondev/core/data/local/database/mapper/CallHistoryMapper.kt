package com.ralphmarondev.core.data.local.database.mapper

import com.ralphmarondev.core.data.local.database.entities.CallHistoryEntity
import com.ralphmarondev.core.domain.model.CallHistory

fun CallHistory.toEntity(): CallHistoryEntity {
    return CallHistoryEntity(
        id = id,
        owner = owner,
        name = name,
        number = number,
        type = type,
        date = date,
        duration = duration
    )
}

fun CallHistoryEntity.toDomain(): CallHistory {
    return CallHistory(
        id = id,
        owner = owner,
        name = name,
        number = number,
        type = type,
        date = date,
        duration = duration
    )
}