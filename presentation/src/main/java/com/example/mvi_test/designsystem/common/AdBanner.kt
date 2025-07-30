package com.example.mvi_test.designsystem.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.example.mvi_test.util.AdMobType
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import timber.log.Timber

@Composable
fun CommonAdBanner(
    adMobType: AdMobType,
    modifier:  Modifier = Modifier
) {
    val context = LocalContext.current
    val adView = remember {
        AdView(context).apply {
            adUnitId = adMobType.key
            setAdSize(adMobType.size)
            loadAd(AdRequest.Builder().build())
            adListener = object : AdListener() {
                override fun onAdLoaded() { Timber.e("Banner ad was loaded.") }
                override fun onAdFailedToLoad(error: LoadAdError) { Timber.e("Banner ad failed to load: ${error.message}") }
                override fun onAdImpression() { Timber.e("Banner ad recorded an impression.") }
                override fun onAdClicked() { Timber.e("Banner ad was clicked.") }
            }
        }
    }

    LifecycleResumeEffect(adView) {
        adView.resume()
        onPauseOrDispose { adView.pause() }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        AndroidView(
            modifier = modifier.wrapContentSize(),
            factory = { adView },
        )
    }

    DisposableEffect(Unit) {
        onDispose { adView.destroy() }
    }

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

}

@Preview
@Composable
private fun CommonAdBannerPreview() {
    CommonAdBanner(AdMobType.AdMobDialogBanner())
}