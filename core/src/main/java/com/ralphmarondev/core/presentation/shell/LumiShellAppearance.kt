package com.ralphmarondev.core.presentation.shell

import androidx.compose.ui.graphics.Color

data class LumiShellAppearance(
    val foregroundColor: Color = Color.White,
    val backgroundColor: Color = Color.Transparent,
    val showBackground: Boolean = false,
    val iconAlpha: Float = 1f
)