package com.ralphmarondev.clock

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class ClockState(
    val screens: List<ClockScreen> = emptyList()
)

data class ClockScreen(
    val icon: ImageVector,
    val label: String,
    @Serializable
    val route: Routes
)