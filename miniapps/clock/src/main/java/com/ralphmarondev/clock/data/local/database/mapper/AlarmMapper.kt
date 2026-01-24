package com.ralphmarondev.clock.data.local.database.mapper

import com.ralphmarondev.clock.data.local.database.entities.AlarmEntity
import com.ralphmarondev.clock.domain.model.Alarm

fun AlarmEntity.toDomain(): Alarm {
    return Alarm(
        id = id,
        hour = hour,
        minute = minute,
        label = label,
        isEnabled = isEnabled,
        repeatDays = repeatDays,
        ringtoneUri = ringtoneUri,
        vibrate = vibrate
    )
}

fun Alarm.toEntity(): AlarmEntity {
    return AlarmEntity(
        id = id,
        hour = hour,
        minute = minute,
        label = label,
        isEnabled = isEnabled,
        repeatDays = repeatDays,
        ringtoneUri = ringtoneUri,
        vibrate = vibrate
    )
}