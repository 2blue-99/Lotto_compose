package com.lucky_lotto.mvi_test

import com.lucky_lotto.data.util.connect.NetworkMonitor
import com.lucky_lotto.mvi_test.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor
): BaseViewModel() {
//    val isConnected = networkMonitor
//        .isOnline
//        .stateIn(
//            scope = ioScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = false
//        )
}