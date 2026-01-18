package com.ralphmarondev.core.data.local.database.converters

import androidx.room.TypeConverter
import com.ralphmarondev.core.domain.model.Gender
import com.ralphmarondev.core.domain.model.Role

class EnumConverters {

    @TypeConverter
    fun fromGender(value: Gender): String = value.name

    @TypeConverter
    fun toGender(value: String?): Gender = runCatching {
        Gender.valueOf(value ?: "")
    }.getOrDefault(Gender.NotSet)

    @TypeConverter
    fun fromRole(value: Role): String = value.name

    @TypeConverter
    fun toRole(value: String?): Role = runCatching {
        Role.valueOf(value ?: "")
    }.getOrDefault(Role.User)
}