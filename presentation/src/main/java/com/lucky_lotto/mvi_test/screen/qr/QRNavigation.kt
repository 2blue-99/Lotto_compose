package com.lucky_lotto.mvi_test.screen.qr

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.StateFlow

const val qrScannerRoute = "qr_scanner"
fun NavController.navigateToQR(navOptions: NavOptions? = null){
    this.navigate(qrScannerRoute, navOptions)
}

fun NavGraphBuilder.qrScannerScreen(
    popBackStack: () -> Unit,
    showFrontPageAd: () -> StateFlow<Boolean>,
    modifier: Modifier = Modifier,
){
    composable(
        route = qrScannerRoute,
    ) {
        QRScannerRouth(
            popBackStack,
            showFrontPageAd,
            modifier
        )
    }
}