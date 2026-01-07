package com.ralphmarondev.core.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun ThemeToggle(
    darkTheme: Boolean,
    toggleTheme: () -> Unit
) {
    IconButton(onClick = toggleTheme) {
        Icon(
            imageVector = if (darkTheme) {
                Icons.Outlined.LightMode
            } else {
                Icons.Outlined.DarkMode
            },
            contentDescription = if (darkTheme) {
                "Switch to light mode"
            } else {
                "Switch to dark mode"
            }
        )
    }
}