package com.ralphmarondev.core.presentation.shell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

val LocalLumiShellState = compositionLocalOf<LumiShellState> {
    error("No LumiShellState provided. Make sure to wrap your UI with LumiShellProvider.")
}

class LumiShellState internal constructor(
    initial: LumiShellAppearance
) {
    private val _appearance = mutableStateOf(initial)
    val appearance: State<LumiShellAppearance> = _appearance

    fun setAppearance(style: LumiShellStyle) {
        _appearance.value = style.toAppearance()
    }

    fun reset() {
        _appearance.value = LumiShellAppearance()
    }
}

@Composable
fun rememberLumiShellState(): LumiShellState {
    return remember {
        LumiShellState(
            initial = LumiShellAppearance()
        )
    }
}

enum class LumiShellStyle {
    BlackOnTransparent,
    WhiteOnTransparent,
    WhiteOnDim
}

fun LumiShellStyle.toAppearance() = when (this) {
    LumiShellStyle.BlackOnTransparent -> {
        LumiShellAppearance(
            foregroundColor = Color(0xFF1A1A1A),
            backgroundColor = Color.Transparent
        )
    }

    LumiShellStyle.WhiteOnTransparent -> {
        LumiShellAppearance(
            foregroundColor = Color.White,
            backgroundColor = Color.Transparent
        )
    }

    LumiShellStyle.WhiteOnDim -> {
        LumiShellAppearance(
            foregroundColor = Color.White,
            backgroundColor = Color(0x33000000) // black at ~20% opacity
        )
    }
}