package com.ralphmarondev.system.settings.data.repository

import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.system.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first

class SettingsRepositoryImpl(
    private val preferences: AppPreferences
) : SettingsRepository {
    override suspend fun setEnableAuthKey(value: Boolean) {
        preferences.setSystemEnableAuth(value)
    }

    override suspend fun isAuthEnabled(): Boolean {
        return preferences.isSystemEnableAuth().first()
    }
}