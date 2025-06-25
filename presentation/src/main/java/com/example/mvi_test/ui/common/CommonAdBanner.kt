package com.example.mvi_test.ui.common

import android.webkit.WebView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CommonAdBanner(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = 6f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 800, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
    ) {
        Surface(
            elevation = 2.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(16),
            modifier = modifier
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