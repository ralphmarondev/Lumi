package com.ralphmarondev.system.settings.presentation.overview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.core.presentation.component.LumiLottie
import com.ralphmarondev.core.presentation.theme.LocalThemeState
import com.ralphmarondev.system.R

@Composable
fun OverviewScreenRoot() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OverviewScreen() {
    val themeState = LocalThemeState.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Settings")
                },
                actions = {
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary
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
                LumiLottie(
                    animatedResId = R.raw.under_development,
                    modifier = Modifier
                        .size(200.dp)
                )
            }
        }
    }
}