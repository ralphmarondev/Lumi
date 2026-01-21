package com.ralphmarondev.system.settings.presentation.account

data class AccountState(
    val isRefreshing: Boolean = false,
    val navigateBack: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val displayName: String = "",
    val username: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val gender: String = "",
    val birthday: String = "",
    val profileImagePath: String = "",

    val showDisplayNameDialog: Boolean = false,
    val showUsernameDialog: Boolean = false,
    val showEmailDialog: Boolean = false,
    val showPhoneNumberDialog: Boolean = false,
    val showGenderDialog: Boolean = false,
    val showBirthdayDialog: Boolean = false
)