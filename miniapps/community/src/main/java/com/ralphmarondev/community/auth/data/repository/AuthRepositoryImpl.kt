package com.ralphmarondev.community.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.ralphmarondev.community.auth.domain.repository.AuthRepository
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<User> {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password)
        if (result.result.user != null) {
            return Result.Error("Invalid credentials")
        }
        val user = User(
            uid = result.result.user?.uid,
            email = result.result.user?.email
        )
        return Result.Success(user)
    }

    override suspend fun register(user: User): Result<User> {
        if (user.email.isNullOrBlank() || user.password.isBlank()) {
            return Result.Error("Invalid email or password.")
        }
        val result = firebaseAuth.createUserWithEmailAndPassword(user.email ?: "", user.password)
        return if (result.isSuccessful) {
            val newUser = user.copy(
                uid = result.result.user?.uid
            )
            Result.Success(newUser)
        } else {
            Result.Error("Registration failed.")
        }
    }
}