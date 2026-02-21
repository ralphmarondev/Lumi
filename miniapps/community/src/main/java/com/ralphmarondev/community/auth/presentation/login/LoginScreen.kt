package com.ralphmarondev.community.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreenRoot(
    navigateBack: () -> Unit,
    onRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onLoginSuccess()
            viewModel.onAction(LoginAction.ClearNavigation)
        }
    }

    LaunchedEffect(state.navigateToRegister) {
        if (state.navigateToRegister) {
            onRegister()
            viewModel.onAction(LoginAction.ClearNavigation)
        }
    }

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
            viewModel.onAction(LoginAction.ClearNavigation)
        }
    }

    LumiGestureHandler(onBackSwipe = navigateBack) {

    }
}

@Composable
private fun LoginScreen(
    state: LoginState,
    action: (LoginAction) -> Unit
) {

}