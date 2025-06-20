package com.example.mvi_test.util

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class HiltApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }
    private fun initTimber(){
        Timber.plant(LogDebugTree("Lotto_App"))
    }
}