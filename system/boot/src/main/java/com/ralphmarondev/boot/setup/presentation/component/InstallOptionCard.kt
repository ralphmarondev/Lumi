package com.ralphmarondev.boot.setup.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ralphmarondev.boot.setup.presentation.setup.InstallMode

@Composable
fun InstallOptionCard(
    option: InstallMode,
    selectedOption: InstallMode,
    title: String,
    subtitle: String,
    onSelect: (InstallMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val selected = option == selectedOption
    val colors: CardColors = if (selected) {
        CardDefaults.cardColors()
    } else {
        CardDefaults.outlinedCardColors()
    }

    OutlinedCard(
        onClick = { onSelect(option) },
        modifier = modifier,
        colors = colors
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.padding(vertical = 8.dp)) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onSelect(option) },
                    modifier = Modifier.size(18.dp)
                )
            }
            Column(
                modifier = Modifier.padding(start = 18.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}