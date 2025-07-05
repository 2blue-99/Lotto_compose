package com.example.mvi_test.designsystem.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CommonAdBanner(modifier:  Modifier = Modifier) {
    val context = LocalContext.current

    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = 8f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 800, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(
        modifier = modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            elevation = 2.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(16),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .offset { IntOffset(x = 0, y = offsetY.value.roundToInt()) }
        ) {
//        AdView(context).apply {
//            adSize = AdSize.BANNER
//            adUnitId = ""
//            loadAd(AdRequest.Builder().build())
//        }
        }
    }
}

@Preview
@Composable
private fun CommonAdBannerPreview() {
    CommonAdBanner()
}