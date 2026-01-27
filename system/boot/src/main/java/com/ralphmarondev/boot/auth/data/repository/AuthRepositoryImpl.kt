package com.ralphmarondev.boot.auth.data.repository

import com.ralphmarondev.boot.auth.domain.repository.AuthRepository
import com.ralphmarondev.core.data.local.database.dao.UserDao
import com.ralphmarondev.core.data.local.database.mapper.toDomain
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User

class AuthRepositoryImpl(
    private val userDao: UserDao,
    private val preferences: AppPreferences
) : AuthRepository {
    override suspend fun login(
        username: String,
        password: String
    ): Result<User> {
        return try {
            val userEntity = userDao.login(username, password)
            if (userEntity != null) {
                preferences.setSystemOnboardingCompleted(true)
                preferences.setSystemIsAuthenticated(true)
                preferences.setSystemCurrentUser(username)
                return Result.Success(userEntity.toDomain())
            }
            Result.Error(message = "Invalid credentials.")
        } catch (e: Exception) {
            Result.Error(message = e.localizedMessage)
        }
    }
}