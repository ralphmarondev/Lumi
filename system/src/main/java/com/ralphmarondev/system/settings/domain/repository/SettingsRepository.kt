package com.ralphmarondev.system.settings.domain.repository

import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User

interface SettingsRepository {
    suspend fun setEnableAuthKey(value: Boolean)

    suspend fun isAuthEnabled(): Boolean

    suspend fun getUserInformation(): Result<User>

    suspend fun updateUserInformation(user: User): Result<User>
}