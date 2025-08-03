package com.lucky_lotto.mvi_test.screen.setting.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.lucky_lotto.mvi_test.screen.setting.screen.SettingRoute

const val settingRoute = "setting"
fun NavController.navigateToSetting(navOptions: NavOptions? = null){
    this.navigate(settingRoute, navOptions)
}

fun NavGraphBuilder.settingScreen(
    modifier: Modifier,
){
    composable(
        route = settingRoute,
    ) {
        SettingRoute(
            modifier
        )
    }
}