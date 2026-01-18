package com.ralphmarondev.core.domain.model

data class User(
    val username: String = "",
    val password: String = "",
    val displayName: String = "",
    val email: String? = null,
    val phoneNumber: String? = null,
    val gender: Gender = Gender.NotSet,
    val birthday: String = "",
    val profileImagePath: String? = null,
    val description: String = "",
    val role: Role = Role.User,
    val language: String = "English",
    val street: String = "",
    val city: String = "",
    val province: String = "",
    val postalCode: String = "",
    val country: String = ""
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