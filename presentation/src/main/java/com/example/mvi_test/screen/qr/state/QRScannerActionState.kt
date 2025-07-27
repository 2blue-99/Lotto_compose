package com.example.mvi_test.screen.random.state

sealed class QRScannerActionState {
    data object UpdateRequireCameraPermission : QRScannerActionState()
}