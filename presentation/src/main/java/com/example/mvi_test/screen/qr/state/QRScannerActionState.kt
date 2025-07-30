package com.example.mvi_test.screen.qr.state

import com.example.domain.type.DialogType


sealed class QRScannerActionState {
    // 카메라 권한 요청 여부 저장
    data object UpdateRequireCameraPermission : QRScannerActionState()

    data class ShowPermissionDialog(val dialogType: DialogType): QRScannerActionState()
    data object HidePermissionDialog: QRScannerActionState()

    data class ShowAdDialog(val dialogType: DialogType, val url: String): QRScannerActionState()
    data object HideAdDialog: QRScannerActionState()
}