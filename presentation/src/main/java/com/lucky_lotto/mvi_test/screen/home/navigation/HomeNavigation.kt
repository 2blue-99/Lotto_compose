package com.lucky_lotto.mvi_test.screen.home.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.lucky_lotto.mvi_test.screen.home.screen.HomeRoute

const val homeRoute = "home"
fun NavController.navigateToHome(navOptions: NavOptions? = null){
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToQR: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToRandom: () -> Unit,
    navigateToRecode: () -> Unit,
    navigateToStatistic: () -> Unit,
    modifier: Modifier
){
    composable(
        route = homeRoute,
    ) {
        HomeRoute(
            navigateToQR,
            navigateToSetting,
            navigateToRandom,
            navigateToRecode,
            navigateToStatistic,
            modifier
        )
    }
}