package com.ralphmarondev.core.presentation.shell

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalLumiShellAppearance = compositionLocalOf {
    LumiShellAppearance(
        foregroundColor = Color.White,
        backgroundColor = Color.Transparent,
        showBackground = false
    )
}