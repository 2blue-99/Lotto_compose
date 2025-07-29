package com.example.mvi_test.screen.setting.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.mvi_test.screen.setting.screen.SettingRoute

const val settingRoute = "setting"
fun NavController.navigateToSetting(navOptions: NavOptions? = null){
    this.navigate(settingRoute, navOptions)
}

fun NavGraphBuilder.settingScreen(
    popBackStack: () -> Unit,
    modifier: Modifier,
){
    composable(
        route = settingRoute,
//        exitTransition = { slideToLeftExit() },
//        popEnterTransition = { slideToRightEnter() }
    ) {
        SettingRoute(
            modifier
        )
    }
}