package com.lucky_lotto.mvi_test.screen.qr.state

sealed interface QRScannerUIState {

    data object Loading : QRScannerUIState

    data object Fail : QRScannerUIState

    data class Success(
        val isRequiredCamera: Boolean
    ) : QRScannerUIState
}