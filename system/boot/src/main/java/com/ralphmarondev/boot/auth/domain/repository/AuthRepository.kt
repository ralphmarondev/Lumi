package com.ralphmarondev.boot.auth.domain.repository

import com.ralphmarondev.core.domain.model.User
import com.ralphmarondev.core.domain.model.Result

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<User>
}