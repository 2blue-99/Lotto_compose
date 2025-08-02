package com.lucky_lotto.mvi_test.util

import android.app.Activity
import android.widget.Toast
import com.lucky_lotto.domain.util.CommonMessage
import com.lucky_lotto.mvi_test.BuildConfig
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 광고 유틸 클래스
 */
class AdMobUtil(
    private val activity: Activity,
) {
    val _isAdFinish = MutableStateFlow(false)
    val isAdFinish = _isAdFinish.asStateFlow()
    private var frontPageAd: InterstitialAd? = null
//    private var rewardedAd: RewardedAd? = null

    init {
        Timber.e("광고 기능 만들어짐")
        CoroutineScope(Dispatchers.IO).launch {
            MobileAds.initialize(activity) // 애드몹 광고 초기화
        }
        loadFrontPageAd()
    }

    /**
     * 전면 광고 시작
     */
    fun showFrontPageAd(): StateFlow<Boolean> {
        _isAdFinish.value = false
        frontPageAd?.show(activity)
            ?: Toast.makeText(activity, CommonMessage.ADMOB_NOT_LOAD_YET.message, Toast.LENGTH_SHORT).show()
        return isAdFinish
    }

    /**
     * 전면 광고 로드
     */
    private fun loadFrontPageAd(){
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
                    // 광고 종료
                    Timber.e("front page ad was dismissed.")
                    frontPageAd = null
                    loadFrontPageAd()
                    _isAdFinish.value = true
                }
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    // 광고 노출 실패
                    Timber.e("front page ad failed to show. $adError")
                    frontPageAd = null
                    loadFrontPageAd()
                    _isAdFinish.value = true
                }
                override fun onAdShowedFullScreenContent() {
                    // 전면에 광고 노출
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

//    fun showRewordAd(){
//        rewardedAd?.show(activity, OnUserEarnedRewardListener { rewardItem ->
//            Timber.d("User earned the reward")
//            Timber.d("rewardItem.amount : ${rewardItem.amount}")
//            Timber.d("rewardItem.type : ${rewardItem.type}")
//        })
//            ?: Toast.makeText(activity, CommonMessage.ADMOB_NOT_LOAD_YET.message, Toast.LENGTH_SHORT).show()
//    }

//    private fun loadRewordAd(){
//        RewardedAd.load(
//            activity,
//            BuildConfig.AD_REWORD_ID,
//            AdRequest.Builder().build(),
//            object : RewardedAdLoadCallback() {
//                override fun onAdLoaded(ad: RewardedAd) {
//                    Timber.d("onAdLoaded reword")
//                    rewardedAd = ad
//                }
//
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    Timber.d("onAdFailedToLoad reword : $adError")
//                    rewardedAd = null
//                }
//            },
//        )
//    }
}