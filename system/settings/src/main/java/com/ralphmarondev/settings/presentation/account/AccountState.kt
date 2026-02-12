package com.ralphmarondev.settings.presentation.account

import com.ralphmarondev.core.domain.model.Gender

data class AccountState(
    val isRefreshing: Boolean = false,
    val navigateBack: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val displayName: String = "",
    val username: String = "",
    val email: String? = null,
    val phoneNumber: String? = null,
    val gender: Gender = Gender.NotSet,
    val genderString: String = "Not set",
    val birthday: String? = null,
    val profileImagePath: String? = null,

    val showDisplayNameDialog: Boolean = false,
    val showUsernameDialog: Boolean = false,
    val showEmailDialog: Boolean = false,
    val showPhoneNumberDialog: Boolean = false,
    val showGenderDialog: Boolean = false,
    val showBirthdayDialog: Boolean = false
)