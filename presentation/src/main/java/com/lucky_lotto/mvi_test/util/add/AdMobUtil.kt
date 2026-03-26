package com.lucky_lotto.mvi_test.util.add

import android.app.Activity
import android.widget.Toast
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.lucky_lotto.domain.util.CommonMessage
import com.lucky_lotto.mvi_test.BuildConfig
import com.lucky_lotto.mvi_test.util.AppEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 광고 유틸 클래스 (QR 전면 광고 전용)
 */
class AdMobUtil(
    private val activity: Activity,
) {
    val _isAdFinish = MutableStateFlow(false)
    val isAdFinish = _isAdFinish.asStateFlow()
    private var frontPageAd: InterstitialAd? = null

    init {
        Timber.e("광고 기능 만들어짐")
        CoroutineScope(Dispatchers.IO).launch {
            MobileAds.initialize(activity)
        }
        loadFrontPageAd()
    }

    /**
     * 전면 광고 시작
     */
    fun showFrontPageAd(): StateFlow<Boolean> {
        AppEvent.log(AppEvent.Event.AdFrontVideoShown)
        _isAdFinish.value = false
        frontPageAd?.show(activity)
            ?: run {
                Toast.makeText(activity, CommonMessage.ADMOB_LOAD_FAIL.message, Toast.LENGTH_SHORT).show()
                _isAdFinish.value = true
            }
        return isAdFinish
    }

    /**
     * 전면 광고 로드
     */
    private fun loadFrontPageAd() {
        InterstitialAd.load(
            activity,
            BuildConfig.AD_FULL_PAGE_ID,
            AdRequest.Builder().build(),
            fontPageAddCallback()
        )
    }

    /**
     * 전면 광고 콜백
     *
     * 변수로 만들게되면 초기화가 안되어서 에러발생
     */
    private fun fontPageAddCallback() = object : InterstitialAdLoadCallback() {
        override fun onAdLoaded(ad: InterstitialAd) {
            Timber.e("onAdLoaded front page")
            frontPageAd = ad
            frontPageAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Timber.e("front page ad was dismissed.")
                    frontPageAd = null
                    loadFrontPageAd()
                    _isAdFinish.value = true
                }
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Timber.e("front page ad failed to show. $adError")
                    frontPageAd = null
                    loadFrontPageAd()
                    _isAdFinish.value = true
                }
                override fun onAdShowedFullScreenContent() {
                    Timber.e("front page ad showed fullscreen content.")
                }
                override fun onAdImpression() {
                    Timber.e("front page ad recorded an impression.")
                }
                override fun onAdClicked() {
                    Timber.e("front page ad was clicked.")
                }
            }
        }

        override fun onAdFailedToLoad(adError: LoadAdError) {
            Timber.e("onAdFailedToLoad front page : ${adError.message}")
            frontPageAd = null
        }
    }
}
