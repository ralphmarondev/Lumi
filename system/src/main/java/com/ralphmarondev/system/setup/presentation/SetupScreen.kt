package com.ralphmarondev.system.setup.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.core.presentation.component.LumiButton
import com.ralphmarondev.core.presentation.component.LumiLottie
import com.ralphmarondev.core.presentation.component.LumiPasswordField
import com.ralphmarondev.core.presentation.component.LumiTextField
import com.ralphmarondev.core.presentation.shell.LocalLumiShellState
import com.ralphmarondev.core.presentation.shell.LumiShellAppearance
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import com.ralphmarondev.system.R
import com.ralphmarondev.system.setup.presentation.component.LanguageCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetupScreenRoot(
    onCompleted: () -> Unit
) {
    val viewModel: SetupViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val shellState = LocalLumiShellState.current

    LaunchedEffect(Unit) {
        shellState.setAppearance(
            LumiShellAppearance(
                foregroundColor = Color(0xFF1A1A1A),
                backgroundColor = Color.Transparent
            )
        )
    }

    LaunchedEffect(state.completed) {
        if (state.completed) {
            onCompleted()
            viewModel.onAction(SetupAction.ResetNavigation)
        }
    }

    SetupScreen(
        state = state,
        action = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SetupScreen(
    state: SetupState,
    action: (SetupAction) -> Unit
) {
    val themeState = LocalThemeState.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message) {
        state.message?.let { msg ->
            snackbarHostState.showSnackbar(message = msg)
            action(SetupAction.ResetMessage)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    AnimatedVisibility(
                        visible = state.currentScreen > 0
                    ) {
                        IconButton(
                            onClick = { action(SetupAction.Previous) }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBackIosNew,
                                contentDescription = "Previous"
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { themeState.toggleTheme() }) {
                        val darkMode = themeState.darkTheme.value
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    actionIconContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                LumiButton(
                    text = "Continue",
                    onClick = { action(SetupAction.Continue) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.enableContinueButton
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                when (state.currentScreen) {
                    0 -> ChooseLanguage(state, action)
                    1 -> AboutLumi()
                    2 -> PrivacyAndSecurity()
                    3 -> CreateAccount(state, action)
                    4 -> AllSet()
                }
            }
        }
    }
}

@Composable
private fun ChooseLanguage(
    state: SetupState,
    action: (SetupAction) -> Unit
) {
    LumiLottie(
        animatedResId = R.raw.language,
        modifier = Modifier
            .size(140.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Choose Language",
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "This can be changed anytime",
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )
    )

    Spacer(modifier = Modifier.height(32.dp))
    LanguageCard(
        text = "English",
        onClick = { action(SetupAction.SetLanguage(0)) },
        modifier = Modifier.padding(vertical = 4.dp),
        selected = state.selectedLanguage == 0
    )
    LanguageCard(
        text = "Filipino",
        onClick = { action(SetupAction.SetLanguage(1)) },
        modifier = Modifier.padding(vertical = 4.dp),
        selected = state.selectedLanguage == 1
    )
}

@Composable
private fun AboutLumi() {
    Image(
        painter = rememberAsyncImagePainter(model = R.drawable.lumi_logo),
        contentDescription = null,
        modifier = Modifier
            .size(140.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Lumi",
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.primary
        )
    )

    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "A virtual operating system",
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Normal
        )
    )

    Spacer(modifier = Modifier.height(32.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "Unified experience",
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = "Every feature feels native to the system, not separate apps.",
            color = MaterialTheme.colorScheme.secondary
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "Intentionally simple",
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = "Built for clarity and focus, without distractions.",
            color = MaterialTheme.colorScheme.secondary
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "Designed to feel right",
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = "Consistent behavior across every part of the system",
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun PrivacyAndSecurity() {
    LumiLottie(
        animatedResId = R.raw.download_and_storage,
        modifier = Modifier
            .size(120.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Privacy and Security",
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "Your data stays on your device. Everything is encrypted under your control.",
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )
    )

    Spacer(modifier = Modifier.height(32.dp))
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "All data is stored locally on your device.",
            modifier = Modifier.padding(16.dp)
        )
    }
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "Industry-standard encryption protects your information.",
            modifier = Modifier.padding(16.dp)
        )
    }
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "No analytics or tracking of any kind.",
            modifier = Modifier.padding(16.dp)
        )
    }
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "Delete or export your data whenever you want.",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun CreateAccount(
    state: SetupState,
    action: (SetupAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    LumiLottie(
        animatedResId = R.raw.create_account,
        modifier = Modifier
            .size(140.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Create Account",
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "You'll use this to sign in",
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )
    )

    Spacer(modifier = Modifier.height(32.dp))

    LumiTextField(
        value = state.username,
        onValueChange = { action(SetupAction.UsernameChange(it)) },
        leadingIconImageVector = Icons.Outlined.AccountBox,
        labelText = "Username",
        placeHolderText = "Enter username",
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Next) }
        )
    )
    LumiPasswordField(
        value = state.password,
        onValueChange = { action(SetupAction.PasswordChange(it)) },
        leadingIconImageVector = Icons.Outlined.Password,
        labelText = "Password",
        placeholderText = "Enter password",
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Next) }
        )
    )
    LumiPasswordField(
        value = state.confirmPassword,
        onValueChange = { action(SetupAction.ConfirmPasswordChange(it)) },
        leadingIconImageVector = Icons.Outlined.Password,
        labelText = "Confirm Password",
        placeholderText = "Re-enter password",
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
}

@Composable
private fun AllSet() {
    LumiLottie(
        animatedResId = R.raw.success,
        modifier = Modifier
            .size(240.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "All Set",
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "Your system is ready.",
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )
    )
}