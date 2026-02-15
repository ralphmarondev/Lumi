package com.ralphmarondev.boot.setup.presentation.install

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
private fun InstallScreen(
    state: InstallState,
    action: (InstallAction) -> Unit
) {

}