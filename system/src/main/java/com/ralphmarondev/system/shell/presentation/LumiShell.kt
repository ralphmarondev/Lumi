package com.ralphmarondev.system.shell.presentation

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalCellular4Bar
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.ralphmarondev.core.presentation.shell.LocalLumiShellState
import com.ralphmarondev.core.presentation.shell.LumiShellState
import com.ralphmarondev.core.presentation.shell.rememberLumiShellState
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun LumiShell(
    content: @Composable () -> Unit
) {
    val shellState = rememberLumiShellState()
    CompositionLocalProvider(
        LocalLumiShellState provides shellState
    ) {
        LumiShellLayout(
            shellState = shellState,
            content = content
        )
    }
}

@Composable
private fun LumiShellLayout(
    shellState: LumiShellState,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    var currentTime by remember { mutableStateOf(Calendar.getInstance().time) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Calendar.getInstance().time
            delay(1000)
        }
    }
    val timeFormatter = remember { SimpleDateFormat("h:mm a", Locale.getDefault()) }
    val (batteryLevel, isCharging) = rememberBatteryLevel(context)

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .background(shellState.appearance.value.backgroundColor)
                .zIndex(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = timeFormatter.format(currentTime),
                color = shellState.appearance.value.foregroundColor,
                fontSize = 13.sp,
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.SignalCellular4Bar,
                    contentDescription = null,
                    tint = shellState.appearance.value.foregroundColor,
                    modifier = Modifier.size(18.dp)
                )
                Icon(
                    imageVector = Icons.Filled.Wifi,
                    contentDescription = null,
                    tint = shellState.appearance.value.foregroundColor,
                    modifier = Modifier.size(18.dp)
                )
                LumiBatteryIndicator(
                    level = batteryLevel,
                    charging = isCharging,
                    foreGroundColor = shellState.appearance.value.foregroundColor
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }
    }
}

@Composable
private fun rememberBatteryLevel(context: Context): Pair<Int, Boolean> {
    var batteryLevel by remember { mutableIntStateOf(86) }
    var isCharging by remember { mutableStateOf(false) }

    DisposableEffect(context) {
        val receiver = object : android.content.BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent ?: return

                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                batteryLevel = if (level >= 0 && scale > 0) level * 100 / scale else 0

                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL
            }
        }

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(receiver, filter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    return batteryLevel to isCharging
}

@Composable
private fun LumiBatteryIndicator(
    level: Int,         // 0..100
    modifier: Modifier = Modifier,
    charging: Boolean = false,
    foreGroundColor: Color = Color.White
) {
    val clampedLevel = level.coerceIn(0, 100)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(width = 36.dp, height = 16.dp)
                .background(
                    color = foreGroundColor.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(3.dp)
                )
                .padding(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(clampedLevel / 100f)
                    .background(
                        color = if (charging) Color.Green else foreGroundColor,
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
        Text(
            text = "$clampedLevel%",
            color = foreGroundColor,
            fontSize = 13.sp,
            style = MaterialTheme.typography.bodySmall
        )
    }
}