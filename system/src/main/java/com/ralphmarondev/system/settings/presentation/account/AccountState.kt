package com.ralphmarondev.system.settings.presentation.account

import com.ralphmarondev.core.domain.model.User

data class AccountState(
    val user: User = User(),
    val isRefreshing: Boolean = false,
    val navigateBack: Boolean = false,
    val editField: EditField? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)

enum class EditField {
    DISPLAY_NAME, USERNAME, EMAIL, PHONE, GENDER, BIRTHDAY
}