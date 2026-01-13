package com.ralphmarondev.system.settings.domain.repository

interface SettingsRepository {
    suspend fun setEnableAuthKey(value: Boolean)

    suspend fun isAuthEnabled(): Boolean
}