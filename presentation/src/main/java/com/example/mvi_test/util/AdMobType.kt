package com.example.mvi_test.util

import android.content.Context
import com.example.mvi_test.BuildConfig
import com.google.android.gms.ads.AdSize

sealed interface AdMobType {
    val size: AdSize
    val key: String

    data class AdMobBottomBanner(
        val context: Context,
        val width: Int,
        override val size: AdSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, width),
        override val key: String = BuildConfig.AD_BOTTOM_BANNER_ID
    ): AdMobType
    data class AdMobDialogBanner(
        override val size: AdSize = AdSize.MEDIUM_RECTANGLE,
        override val key: String = BuildConfig.AD_DIALOG_BANNER_ID
    ): AdMobType
}