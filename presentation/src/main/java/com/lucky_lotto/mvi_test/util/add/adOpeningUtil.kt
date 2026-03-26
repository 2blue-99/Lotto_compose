package com.lucky_lotto.mvi_test.util.add

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.lucky_lotto.mvi_test.BuildConfig
import com.lucky_lotto.mvi_test.util.AppEvent
import com.lucky_lotto.mvi_test.util.isShowRandomAd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date

class adOpeningUtil(private val activity: Activity) {

    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    var isShowingAd = false
    private var loadTime: Long = 0

    init {
        loadAd()
    }

    fun loadAd() {
        if (isLoadingAd || isAdAvailable()) return
        AppEvent.log(AppEvent.Event.AdOpeningShown)
        isLoadingAd = true

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            AppOpenAd.load(
                activity,
                BuildConfig.AD_FULL_OPENING_ID,
                AdRequest.Builder().build(),
                object : AppOpenAd.AppOpenAdLoadCallback() {
                    override fun onAdLoaded(ad: AppOpenAd) {
                        Timber.e("AppOpenAd loaded.")
                        appOpenAd = ad
                        isLoadingAd = false
                        loadTime = Date().time
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Timber.e("AppOpenAd failed to load: ${loadAdError.message}")
                        isLoadingAd = false
                    }
                }
            )
        }
    }

    /**
     * 앱 오픈 광고 표시
     * 광고가 없거나 만료된 경우 onComplete 즉시 호출
     */
    fun showAdIfAvailable(isRandom: Boolean = false, onComplete: () -> Unit = {}) {
        if(isRandom && !isShowRandomAd()) {
            onComplete()
            return
        }

        if (isShowingAd) {
            Timber.e("AppOpenAd is already showing.")
            return
        }
        if (!isAdAvailable()) {
            Timber.e("AppOpenAd is not ready.")
            onComplete()
            loadAd()
            return
        }
        isShowingAd = true
        appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Timber.e("AppOpenAd dismissed.")
                appOpenAd = null
                isShowingAd = false
                onComplete()
                loadAd()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Timber.e("AppOpenAd failed to show: $adError")
                appOpenAd = null
                isShowingAd = false
                onComplete()
                loadAd()
            }

            override fun onAdShowedFullScreenContent() {
                Timber.e("AppOpenAd showed fullscreen content.")
            }

            override fun onAdImpression() {
                Timber.e("AppOpenAd recorded an impression.")
            }

            override fun onAdClicked() {
                Timber.e("AppOpenAd was clicked.")
            }
        }
        appOpenAd?.show(activity)
    }

    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour = 3600000L
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    private fun isAdAvailable(): Boolean {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
    }
}
