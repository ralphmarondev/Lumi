package com.ralphmarondev.system.settings.presentation.overview

import com.ralphmarondev.core.domain.model.User

data class OverviewState(
    val navigateToAccount: Boolean = false,
    val navigateToWallpaper: Boolean = false,
    val navigateBack: Boolean = false,
    val user: User = User()
)
