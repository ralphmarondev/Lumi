package com.ralphmarondev.core.data.local.preferences

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
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
}