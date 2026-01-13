package com.ralphmarondev.core.presentation.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun LumiGestureHandler(
    onBackSwipe: () -> Unit,
    modifier: Modifier = Modifier,
    edgeWidthDp: Float = 32f,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val edgePx = with(density) { edgeWidthDp.dp.toPx() }

    Box(
        modifier = modifier.pointerInput(Unit) {
            detectHorizontalDragGestures { change, dragAmount ->
                val x = change.position.x
                val maxX = size.width

                if ((x <= edgePx && dragAmount > 0) || (x >= maxX - edgePx && dragAmount < 0)) {
                    onBackSwipe()
                }
            }
        }
    ) {
        BackHandler {
            onBackSwipe()
        }
        content()
    }
}