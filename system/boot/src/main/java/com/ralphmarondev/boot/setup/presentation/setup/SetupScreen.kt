package com.ralphmarondev.boot.setup.presentation.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ralphmarondev.boot.R
import com.ralphmarondev.boot.setup.presentation.component.InstallOptionCard
import com.ralphmarondev.boot.setup.presentation.component.LanguageCard
import com.ralphmarondev.core.domain.model.Language
import com.ralphmarondev.core.presentation.component.LumiButton
import com.ralphmarondev.core.presentation.component.LumiLottie
import com.ralphmarondev.core.presentation.component.LumiOutlineButton
import com.ralphmarondev.core.presentation.component.LumiPasswordField
import com.ralphmarondev.core.presentation.component.LumiTextField
import com.ralphmarondev.core.presentation.shell.LocalLumiShellState
import com.ralphmarondev.core.presentation.shell.LumiShellStyle
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetupScreenRoot(
    installLumi: () -> Unit,
    tryLumi: () -> Unit
) {
    val viewModel: SetupViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.completeSetup) {
        if (state.completeSetup && state.installationMode == InstallMode.InstallLumi) {
            installLumi()
        }
    }

    LaunchedEffect(state.installationMode) {
        if (state.installationMode == InstallMode.TryLumi) {
            tryLumi()
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
    val shellState = LocalLumiShellState.current
    val darkMode = themeState.darkTheme.value

    val pagerState = rememberPagerState { state.screenCount }

    LaunchedEffect(state.currentScreen) {
        pagerState.scrollToPage(state.currentScreen)
    }

    LaunchedEffect(darkMode) {
        shellState.setAppearance(
            if (darkMode) LumiShellStyle.WhiteOnTransparent
            else LumiShellStyle.BlackOnTransparent
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    actionIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LumiOutlineButton(
                        text = "Back",
                        onClick = { action(SetupAction.Previous) }
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(state.screenCount) { index ->
                            val active = index == state.currentScreen
                            Box(
                                modifier = Modifier
                                    .size(if (active) 18.dp else 12.dp)
                                    .padding(4.dp)
                                    .background(
                                        color = if (active) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                        shape = CircleShape
                                    )
                            )
                        }
                    }

                    LumiButton(
                        text = if (state.currentScreen == state.screenCount - 1) "Finish" else "Next",
                        onClick = { action(SetupAction.Continue) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> ChooseLanguage(state, action)
                    1 -> TryOrInstallLumi(state, action)
                    2 -> CreateAccount(state, action)
                    3 -> Summary(state)
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
            onClick = { action(SetupAction.SetLanguage(Language.ENGLISH)) },
            modifier = Modifier.padding(vertical = 4.dp),
            selected = state.selectedLanguage == Language.ENGLISH,
            flagResId = R.drawable.flag_us
        )
        LanguageCard(
            text = "Filipino",
            onClick = { action(SetupAction.SetLanguage(Language.FILIPINO)) },
            modifier = Modifier.padding(vertical = 4.dp),
            selected = state.selectedLanguage == Language.FILIPINO,
            flagResId = R.drawable.flag_ph
        )
    }
}

@Composable
private fun TryOrInstallLumi(
    state: SetupState,
    action: (SetupAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LumiLottie(
            animatedResId = R.raw.create_account,
            modifier = Modifier
                .size(140.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "What do you want to do with Lumi?",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Pick an option to get started with Lumi.",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        InstallOptionCard(
            option = InstallMode.InstallLumi,
            selectedOption = state.installationMode,
            title = "Install Lumi",
            subtitle = "Perform a full installation of Lumi so you can access all features anytime you want.",
            onSelect = { action(SetupAction.SetInstallMode(it)) },
            modifier = Modifier.padding(vertical = 8.dp)
        )
        InstallOptionCard(
            option = InstallMode.TryLumi,
            selectedOption = state.installationMode,
            title = "Try Lumi",
            subtitle = "Run Lumi temporarily without installing, perfect if you just want to explore it first.",
            onSelect = { action(SetupAction.SetInstallMode(it)) },
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
private fun CreateAccount(
    state: SetupState,
    action: (SetupAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            value = state.displayName,
            onValueChange = { action(SetupAction.DisplayNameChange(it)) },
            leadingIconImageVector = Icons.Outlined.AccountTree,
            labelText = "Your name",
            placeHolderText = "Ralph Maron Eda",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )
        LumiTextField(
            value = state.username,
            onValueChange = { action(SetupAction.UsernameChange(it)) },
            leadingIconImageVector = Icons.Outlined.AccountBox,
            labelText = "Your username",
            placeHolderText = "ralphmaron",
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
}

@Composable
private fun Summary(
    state: SetupState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Summary")
    }
}