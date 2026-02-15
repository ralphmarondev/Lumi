package com.ralphmarondev.boot.setup.presentation.completed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ralphmarondev.boot.R
import com.ralphmarondev.core.presentation.component.LumiLottie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedScreenRoot(
    onComplete: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
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
    }
}