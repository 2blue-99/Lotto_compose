package com.example.mvi_test.screen.qr.state

import com.example.domain.type.DialogType


sealed class QRScannerActionState {
    // 카메라 권한 요청 여부 저장
    data object UpdateRequireCameraPermission : QRScannerActionState()
    data class ShowDialog(val dialogType: DialogType): QRScannerActionState()
    data object HideDialog: QRScannerActionState()
}