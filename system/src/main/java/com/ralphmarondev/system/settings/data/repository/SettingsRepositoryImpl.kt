package com.ralphmarondev.system.settings.data.repository

import com.ralphmarondev.core.data.local.database.dao.UserDao
import com.ralphmarondev.core.data.local.database.mapper.toDomain
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User
import com.ralphmarondev.system.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first

class SettingsRepositoryImpl(
    private val preferences: AppPreferences,
    private val userDao: UserDao
) : SettingsRepository {
    override suspend fun setEnableAuthKey(value: Boolean) {
        preferences.setSystemEnableAuth(value)
    }

    override suspend fun isAuthEnabled(): Boolean {
        return preferences.isSystemEnableAuth().first()
    }

    override suspend fun getUserInformation(): Result<User> {
        return try {
            val username = preferences.getSystemCurrentUser().first()
            val user = userDao.getByUsername(username)
            Result.Success(user?.toDomain() ?: User())
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed fetching user details.")
        }
    }
}