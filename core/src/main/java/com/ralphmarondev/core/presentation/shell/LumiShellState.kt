package com.ralphmarondev.core.presentation.shell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class LumiShellState internal constructor(
    initial: LumiShellAppearance
) {
    private val _appearance = mutableStateOf(initial)
    val appearance: State<LumiShellAppearance> = _appearance

    fun setAppearance(newAppearance: LumiShellAppearance) {
        _appearance.value = newAppearance
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