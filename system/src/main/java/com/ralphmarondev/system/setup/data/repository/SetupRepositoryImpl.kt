package com.ralphmarondev.system.setup.data.repository

import com.ralphmarondev.core.data.local.database.dao.UserDao
import com.ralphmarondev.core.data.local.database.mapper.toDomain
import com.ralphmarondev.core.data.local.database.mapper.toEntity
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.core.domain.model.Language
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User
import com.ralphmarondev.system.setup.domain.repository.SetupRepository

class SetupRepositoryImpl(
    private val userDao: UserDao,
    private val preferences: AppPreferences
) : SetupRepository {
    override suspend fun getUserByUsername(username: String): User? {
        return userDao.getByUsername(username)?.toDomain()
    }

    override suspend fun setup(
        language: Language,
        username: String,
        password: String
    ): Result<User>? {
        return try {
            val existingUser = userDao.getByUsername(username)
            if(existingUser != null){
                return Result.Error("Username already exists")
            }

            val user = User(
                username = username,
                password = password
            )
            val userEntityId = userDao.create(user.toEntity())
            val savedUserEntity = userDao.getById(userEntityId)
            val savedUser = savedUserEntity?.toDomain() ?: User()

            preferences.setSystemLanguage(language.code)
            preferences.setSystemOnboardingCompleted(true)
            Result.Success(data = savedUser)
        } catch (e: Exception) {
            Result.Error(message = e.localizedMessage)
        }
    }
}