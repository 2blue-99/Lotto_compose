package com.example.mvi_test

import androidx.lifecycle.ViewModel
import com.example.data.util.connect.NetworkMonitor
import com.example.mvi_test.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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