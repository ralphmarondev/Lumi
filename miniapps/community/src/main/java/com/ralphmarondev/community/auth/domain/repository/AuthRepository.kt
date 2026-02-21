package com.ralphmarondev.community.auth.domain.repository

import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(user: User): Result<User>
}