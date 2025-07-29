package com.example.mvi_test.designsystem.common

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.mediation.MediationBannerAd
import timber.log.Timber
import kotlin.math.roundToInt

@Composable
fun CommonAdBanner(modifier:  Modifier = Modifier) {
    val context = LocalContext.current

//    val offsetY = remember { Animatable(0f) }

//    LaunchedEffect(Unit) {
//        offsetY.animateTo(
//            targetValue = 8f,
//            animationSpec = infiniteRepeatable(
//                animation = tween(durationMillis = 800, easing = LinearEasing),
//                repeatMode = RepeatMode.Reverse
//            )
//        )
//    }

//    Box(
//        modifier = modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
//        contentAlignment = Alignment.BottomCenter
//    ) {
//        Surface(
//            elevation = 2.dp,
//            shape = RoundedCornerShape(16),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp)
//                .offset { IntOffset(x = 0, y = offsetY.value.roundToInt()) }
//        ) {


    val adView = remember { AdView(context) }
    adView.adUnitId = "ca-app-pub-9295264656027755/3908031744"
    val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(LocalContext.current, 360)
    adView.setAdSize(adSize)

    LifecycleResumeEffect(adView) {
        adView.resume()
        onPauseOrDispose { adView.pause() }
    }

    Timber.d("나 만들어져?")

    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        AndroidView(modifier = modifier.wrapContentSize(), factory = { adView })
    }

    adView.adListener =
        object : AdListener() {
            override fun onAdLoaded() {
                Timber.e("Banner ad was loaded.")
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                Timber.e("Banner ad failed to load: ${error.message}")
            }

            override fun onAdImpression() {
                Timber.e("Banner ad recorded an impression.")
            }

            override fun onAdClicked() {
                Timber.e("Banner ad was clicked.")
            }
        }

    val adRequest = AdRequest.Builder().build()
    adView.loadAd(adRequest)

    DisposableEffect(Unit) {
        onDispose { adView.destroy() }
    }

//            AndroidView(
//                modifier = Modifier.fillMaxWidth(),
//                factory = {
//                    // 최초 한번 실행
//                    AdView(context).apply {
//                        setAdSize(AdSize.BANNER)
//                        adUnitId = "ca-app-pub-9295264656027755/3908031744"
//                        loadAd(AdRequest.Builder().build())
//                    }
//                },
//                update = { adView ->
//                    // 리컴포지션
//                    adView.loadAd(AdRequest.Builder().build())
//                }
//            )
//        }
//    }
}

@Preview
@Composable
private fun CommonAdBannerPreview() {
    CommonAdBanner()
}