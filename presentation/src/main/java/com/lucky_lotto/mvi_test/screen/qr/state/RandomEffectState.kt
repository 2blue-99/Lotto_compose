package com.lucky_lotto.mvi_test.screen.qr.state

import com.lucky_lotto.domain.util.CommonMessage

sealed class QRScannerEffectState {
    data class ShowToast(val message: CommonMessage): QRScannerEffectState()
    data class ShowSnackbar(val message: CommonMessage): QRScannerEffectState()
    data object PopBackStack: QRScannerEffectState()
}

