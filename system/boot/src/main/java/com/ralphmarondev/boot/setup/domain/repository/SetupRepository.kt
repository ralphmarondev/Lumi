package com.ralphmarondev.boot.setup.domain.repository

import com.ralphmarondev.core.domain.model.Language
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User

interface SetupRepository {
    suspend fun getUserByUsername(username: String): User?
    suspend fun setup(language: Language, username: String, password: String): Result<User>?
}