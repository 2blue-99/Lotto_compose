package com.lucky_lotto.data.util

import com.lucky_lotto.data.util.connect.ConnectivityManagerMonitor
import com.lucky_lotto.data.util.connect.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsModule {

    @Binds
    abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerMonitor
    ): NetworkMonitor
}