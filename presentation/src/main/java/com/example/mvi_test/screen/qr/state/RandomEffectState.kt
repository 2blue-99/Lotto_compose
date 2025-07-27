package com.example.mvi_test.screen.random.state

import com.example.domain.util.CommonMessage

sealed class QRScannerEffectState {
    data class ShowToast(val message: CommonMessage): QRScannerEffectState()
    data class ShowSnackbar(val message: CommonMessage): QRScannerEffectState()
}

