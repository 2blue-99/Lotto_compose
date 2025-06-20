package com.example.mvi_test.screen.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.mvi_test.screen.setting.screen.SettingScreen
import timber.log.Timber

const val settingRoute = "setting"
fun NavController.navigateToSetting(navOptions: NavOptions? = null){
    this.navigate(settingRoute, navOptions)
}

fun NavGraphBuilder.settingScreen(
    popBackStack: () -> Unit
){
    composable(
        route = settingRoute,
//        exitTransition = { slideToLeftExit() },
//        popEnterTransition = { slideToRightEnter() }
    ) {
        SettingScreen(popBackStack)
    }
}