package com.lucky_lotto.mvi_test.util

import android.app.Application
import com.lucky_lotto.mvi_test.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class HiltApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }
    private fun initTimber(){
        if(BuildConfig.DEBUG) {
            Timber.plant(LogDebugTree("Lotto_App"))
        }
    }
}