package com.ralphmarondev.core.presentation.shell

import androidx.compose.runtime.compositionLocalOf

val LocalLumiShellState = compositionLocalOf<LumiShellState> {
    error("No LumiShellState provided. Make sure to wrap your UI with LumiShellProvider.")
}