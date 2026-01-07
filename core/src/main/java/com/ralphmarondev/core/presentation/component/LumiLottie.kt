package com.ralphmarondev.core.presentation.component

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LumiLottie(
    @RawRes animatedResId: Int,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    loop: Boolean = true,
    durationMillis: Int? = null
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(animatedResId)
    )

    val speed = durationMillis?.let {
        composition?.duration?.toFloat()?.div(it) ?: 1f
    } ?: 1f

    LottieAnimation(
        composition = composition,
        iterations = if (loop) LottieConstants.IterateForever else 1,
        speed = speed,
        modifier = modifier.size(size)
    )
}