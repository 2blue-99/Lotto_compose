package com.lucky_lotto.mvi_test.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.lucky_lotto.mvi_test.R
import com.lucky_lotto.mvi_test.screen.home.navigation.homeRoute
import com.lucky_lotto.mvi_test.screen.qr.qrScannerRoute
import com.lucky_lotto.mvi_test.screen.random.navigation.randomRoute
import com.lucky_lotto.mvi_test.screen.recode.navigation.recodeRoute
import com.lucky_lotto.mvi_test.screen.statistic.navigation.statisticRoute


sealed class NavigationItem(
    val title: Int,
    val outLineIcon: ImageVector,
    val fillIcon: ImageVector,
    val route: String
) {
    data object Home: NavigationItem(R.string.home, Icons.Outlined.Home, Icons.Filled.Home, homeRoute)
    data object Random: NavigationItem(R.string.random, Icons.Outlined.Home, Icons.Filled.Home, randomRoute)
    data object Statistic: NavigationItem(R.string.statistic, Icons.Outlined.Create, Icons.Filled.Create, statisticRoute)
    data object Recode: NavigationItem(R.string.recode, Icons.Outlined.Create, Icons.Filled.Create, recodeRoute)
    data object QRScanner: NavigationItem(R.string.qr_scanner, Icons.Outlined.Create, Icons.Filled.Create, qrScannerRoute)
//    data object Setting: NavigationItem(R.string.setting, Icons.Outlined.Settings, Icons.Filled.Settings, settingRoute)



    companion object {
        fun toList(): List<NavigationItem>{
            return listOf(Home, Recode)
        }
    }
}