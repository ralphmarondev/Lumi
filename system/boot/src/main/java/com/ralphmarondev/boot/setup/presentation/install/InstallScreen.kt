package com.ralphmarondev.boot.setup.presentation.install

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.core.presentation.component.LumiButton
import com.ralphmarondev.core.presentation.shell.LocalLumiShellState
import com.ralphmarondev.core.presentation.shell.LumiShellStyle
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InstallScreenRoot(
    onComplete: () -> Unit
) {
    val viewModel: InstallViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.installed) {
        if (state.installed) {
            onComplete()
        }
    }

    InstallScreen(
        state = state,
        action = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InstallScreen(
    state: InstallState,
    action: (InstallAction) -> Unit
) {
    val themeState = LocalThemeState.current
    val shellState = LocalLumiShellState.current
    val darkMode = themeState.darkTheme.value
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(darkMode) {
        shellState.setAppearance(
            if (darkMode) LumiShellStyle.WhiteOnTransparent
            else LumiShellStyle.BlackOnTransparent
        )
    }

    LaunchedEffect(state.showErrorMessage) {
        if (state.showErrorMessage) {
            val result = snackbar.showSnackbar(
                message = state.errorMessage ?: "An error occurred.",
                actionLabel = "Retry",
                withDismissAction = true
            )

            if (result == SnackbarResult.ActionPerformed) {
                action(InstallAction.Retry)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = themeState::toggleTheme) {
                        Icon(
                            imageVector = if (darkMode) {
                                Icons.Outlined.LightMode
                            } else {
                                Icons.Outlined.DarkMode
                            },
                            contentDescription = if (darkMode) {
                                "Switch to light mode"
                            } else {
                                "Switch to dark mode"
                            }
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbar) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Horizontal pager, installing...",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = state.showErrorMessage) {
                LumiButton(
                    onClick = { action(InstallAction.Retry) },
                    text = "Retry",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}