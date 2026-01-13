package com.ralphmarondev.lumi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.SignalCellular4Bar
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LumiShell(
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Fake Status Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp) // typical status bar height
                .background(Color(0xFF1E1E1E))
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side (time)
            Text("9:41", color = Color.White, style = MaterialTheme.typography.bodySmall)

            // Right side (icons)
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.SignalCellular4Bar,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Icon(
                    Icons.Default.Wifi,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Icon(
                    Icons.Default.BatteryFull,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Content area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, bottom = 48.dp) // reserve space for status & nav bars
        ) {
            content()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp) // typical nav bar height
                .align(Alignment.BottomCenter)
                .background(Color(0xFF1E1E1E)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(width = 40.dp, height = 4.dp)
                    .background(Color.Gray)
            ) // mimic home pill

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.Gray)
            ) // mimic back button

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.Gray)
            ) // mimic recent button
        }
    }
}
