package com.ralphmarondev.system.settings.presentation.account

import com.ralphmarondev.core.domain.model.User

data class AccountState(
    val user: User = User(),
    val isRefreshing: Boolean = false,
    val navigateBack: Boolean = false
)