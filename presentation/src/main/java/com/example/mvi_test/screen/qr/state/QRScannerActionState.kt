package com.example.mvi_test.screen.qr.state

import com.example.domain.model.RoundSpinner
import com.example.mvi_test.designsystem.common.DialogInfo
import com.example.mvi_test.screen.home.state.HomeActionState

sealed class QRScannerActionState {
    // 카메라 권한 요청 여부 저장
    data object UpdateRequireCameraPermission : QRScannerActionState()
    data class ShowDialog(val dialogInfo: DialogInfo): QRScannerActionState()
    data object HideDialog: QRScannerActionState()
}