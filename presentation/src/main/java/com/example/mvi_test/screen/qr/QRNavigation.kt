package com.example.mvi_test.screen.qr

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val qrScannerRoute = "qr_scanner"
fun NavController.navigateToQR(navOptions: NavOptions? = null){
    this.navigate(qrScannerRoute, navOptions)
}

fun NavGraphBuilder.qrScannerScreen(
    modifier: Modifier = Modifier,
){
    composable(
        route = qrScannerRoute,
    ) {
        QRScannerRouth(
            modifier
        )
    }
}