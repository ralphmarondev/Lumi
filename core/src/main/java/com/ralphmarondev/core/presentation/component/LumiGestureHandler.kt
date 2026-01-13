package com.ralphmarondev.core.presentation.component

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun LumiGestureHandler(
    onBackSwipe: () -> Unit,
    modifier: Modifier = Modifier,
    edgeWidthDp: Dp = 80.dp,
    maxOffsetDp: Dp = 40.dp,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val edgePx = with(density) { edgeWidthDp.toPx() }
    val maxOffsetPx = with(density) { maxOffsetDp.toPx() }

    var dragOffset by remember { mutableStateOf(0f) }
    val animOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .zIndex(10f)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { change, dragAmount ->
                        val x = change.position.x
                        val maxX = size.width

                        if ((x <= edgePx && dragAmount > 0) || (x >= maxX - edgePx && dragAmount < 0)) {
                            if (dragOffset == 0f) {
                                onBackSwipe()
                            }
                            dragOffset += dragAmount
                            val limitedOffset = dragOffset.coerceIn(-maxOffsetPx, maxOffsetPx)
                            coroutineScope.launch {
                                animOffset.snapTo(limitedOffset)
                            }
                        }
                    },
                    onDragEnd = {
                        coroutineScope.launch {
                            animOffset.animateTo(0f, animationSpec = tween(200))
                        }
                    },
                    onDragCancel = {
                        coroutineScope.launch {
                            animOffset.animateTo(0f, animationSpec = tween(200))
                        }
                    }
                )
            }
    ) {
        BackHandler { onBackSwipe() }

        val overlayAlpha = (abs(animOffset.value) / maxOffsetPx).coerceIn(0f, 0.2f)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = overlayAlpha))
        )

        Box(
            modifier = Modifier.offset {
                IntOffset(animOffset.value.roundToInt(), 0)
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(WindowInsets.statusBars.asPaddingValues())
            ) {
                content()
            }
        }
    }
}