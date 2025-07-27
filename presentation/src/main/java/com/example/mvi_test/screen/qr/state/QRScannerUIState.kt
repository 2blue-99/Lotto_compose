package com.example.mvi_test.screen.random.state

import com.example.domain.model.Keyword

sealed interface QRScannerUIState {

    data object Loading : QRScannerUIState

    data object Fail : QRScannerUIState

    data class Success(
        val isRequiredCamera: Boolean
    ) : QRScannerUIState
}