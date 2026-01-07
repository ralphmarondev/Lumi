package com.ralphmarondev.core.data.local.preferences

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.ralphmarondev.core.domain.model.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppPreferences(
    context: Context
) {
    private val appContext = context.applicationContext
    private val dataStore = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler {
            emptyPreferences()
        },
        produceFile = {
            appContext.preferencesDataStoreFile(DATASTORE_NAME)
        }
    )

    companion object {
        private const val DATASTORE_NAME = "lumi_system_preferences"

        val SYSTEM_DARK_MODE = booleanPreferencesKey("system_dark_mode")
        val SYSTEM_ONBOARDING_COMPLETED = booleanPreferencesKey("system_onboarding_completed")
        val SYSTEM_LANGUAGE = stringPreferencesKey("system_language")
        val SYSTEM_IS_AUTHENTICATED = booleanPreferencesKey("system_is_authenticated")
        val SYSTEM_ENABLE_AUTH = booleanPreferencesKey("system_enable_auth")
    }

    suspend fun setSystemInDarkMode(value: Boolean) {
        dataStore.edit { it[SYSTEM_DARK_MODE] = value }
    }

    fun isSystemInDarkMode(): Flow<Boolean> {
        return dataStore.data.map { it[SYSTEM_DARK_MODE] == true }
    }

    suspend fun setSystemOnboardingCompleted(value: Boolean) {
        dataStore.edit { it[SYSTEM_ONBOARDING_COMPLETED] = value }
    }

    fun isSystemOnboardingCompleted(): Flow<Boolean> {
        return dataStore.data.map {
            it[SYSTEM_ONBOARDING_COMPLETED] ?: false
        }
    }

    suspend fun setSystemLanguage(value: String) {
        dataStore.edit { it[SYSTEM_LANGUAGE] = value }
    }

    fun getSystemLanguage(): Flow<String> {
        return dataStore.data.map { it[SYSTEM_LANGUAGE] ?: Language.ENGLISH.code }
    }

    suspend fun setSystemIsAuthenticated(value: Boolean) {
        dataStore.edit { it[SYSTEM_IS_AUTHENTICATED] = value }
    }

    fun isAuthenticated(): Flow<Boolean> {
        return dataStore.data.map { it[SYSTEM_IS_AUTHENTICATED] ?: false }
    }

    suspend fun setSystemEnableAuth(value: Boolean) {
        dataStore.edit { it[SYSTEM_ENABLE_AUTH] = value }
    }

    fun isSystemEnableAuth(): Flow<Boolean> {
        return dataStore.data.map { it[SYSTEM_ENABLE_AUTH] ?: true }
    }
}