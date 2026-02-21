package com.ralphmarondev.community.auth.presentation.login

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ralphmarondev.core.presentation.component.LumiButton
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import com.ralphmarondev.core.presentation.component.LumiLottie
import com.ralphmarondev.core.presentation.component.LumiOutlineButton
import com.ralphmarondev.core.presentation.component.LumiPasswordField
import com.ralphmarondev.core.presentation.component.LumiTextField
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
        LoginScreen(
            state = state,
            action = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreen(
    state: LoginState,
    action: (LoginAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(state.showErrorMessage) {
        if (state.showErrorMessage) {
            snackbarState.showSnackbar(
                message = state.errorMessage ?: "Login Failed."
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { action(LoginAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                LumiLottie(
                    animatedResId = com.ralphmarondev.core.R.raw.under_development,
                    modifier = Modifier
                        .size(140.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Welcome to Lumi Community",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Sign in to your account to continue.",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                LumiTextField(
                    value = state.email,
                    onValueChange = { action(LoginAction.EmailChange(it)) },
                    leadingIconImageVector = Icons.Outlined.Email,
                    labelText = "Email",
                    placeHolderText = "user@lumi.org",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                LumiPasswordField(
                    value = state.password,
                    onValueChange = { action(LoginAction.PasswordChange(it)) },
                    leadingIconImageVector = Icons.Outlined.Password,
                    labelText = "Password",
                    placeholderText = "Enter password",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))
                LumiButton(
                    text = "Login",
                    onClick = { action(LoginAction.Login) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoggingIn
                )

                Spacer(modifier = Modifier.height(16.dp))
                LumiOutlineButton(
                    text = "Register",
                    onClick = { action(LoginAction.Register) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}