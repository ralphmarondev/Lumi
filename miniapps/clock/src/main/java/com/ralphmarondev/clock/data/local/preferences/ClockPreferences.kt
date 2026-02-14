package com.ralphmarondev.clock.data.local.preferences

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClockPreferences(
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
        private const val DATASTORE_NAME = "clock_preferences"

        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }

    suspend fun setDarkMode(value: Boolean) {
        dataStore.edit { it[DARK_MODE] = value }
    }

    fun isInDarkMode(): Flow<Boolean> {
        return dataStore.data.map { it[DARK_MODE] == true }
    }

    suspend fun setOnboardingCompleted(value: Boolean) {
        dataStore.edit { it[ONBOARDING_COMPLETED] = value }
    }

    fun isOnboardingCompleted(): Flow<Boolean> {
        return dataStore.data.map {
            it[ONBOARDING_COMPLETED] ?: false
        }
    }
}