package com.ralphmarondev.core.domain.model

data class User(
    val username: String = "",
    val password: String = "",
    val displayName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val gender: Gender = Gender.NotSet,
    val birthday: String? = null,
    val profileImagePath: String? = null,
    val description: String? = null,
    val role: Role = Role.User,
    val language: String = "English",
    val street: String? = null,
    val city: String? = null,
    val province: String? = null,
    val postalCode: String? = null,
    val country: String? = null
)

enum class Gender {
    NotSet,
    Male,
    Female
}

enum class Role {
    User,
    SuperUser
}