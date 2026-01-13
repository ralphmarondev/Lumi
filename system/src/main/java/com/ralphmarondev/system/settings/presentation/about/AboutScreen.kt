package com.ralphmarondev.system.settings.presentation.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import com.ralphmarondev.core.presentation.theme.LumiTheme
import com.ralphmarondev.system.R
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AboutScreenRoot(
    navigateBack: () -> Unit
) {
    val viewModel: AboutViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
            viewModel.onAction(AboutAction.ResetNavigation)
        }
    }

    LumiGestureHandler(onBackSwipe = navigateBack) {
        AboutScreen(
            state = state,
            action = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutScreen(
    state: AboutState,
    action: (AboutAction) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "About") },
                navigationIcon = {
                    IconButton(onClick = { action(AboutAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                AppHeader()
                Spacer(modifier = Modifier.height(24.dp))

                AppInfoSection()
            }
        }
    }
}

@Composable
private fun AppHeader() {
    Image(
        painter = painterResource(id = R.drawable.lumi_logo),
        contentDescription = "Lumi",
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
    )

    Text(
        text = "Lumi",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 12.dp)
    )

    Text(
        text = "Virtual OS Environment",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun AppInfoSection() {
    Text(
        text = "Lumi is a virtual operating system experience built inside a single Android app. " +
                "It brings system-style features together in one clean, simple interface.",
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Justify
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Lumi is not a launcher. It’s a small OS-inspired space made for clarity, consistency, and experimentation.",
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Justify
    )

    Spacer(modifier = Modifier.height(16.dp))

    InfoRow(label = "System Version", value = "1.0.0")
    InfoRow(label = "Build", value = "2026.01")
    InfoRow(label = "Built with", value = "Jetpack Compose")
    InfoRow(label = "Developer", value = "Ralph Maron Eda")
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Text(
        text = "$label: $value",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AboutScreenPreview() {
    LumiTheme {
        AboutScreen(
            state = AboutState(),
            action = {}
        )
    }
}