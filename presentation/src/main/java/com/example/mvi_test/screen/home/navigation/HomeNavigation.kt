package com.example.mvi_test.screen.home.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.mvi_test.screen.home.screen.HomeRoute

const val homeRoute = "home"
fun NavController.navigateToHome(navOptions: NavOptions? = null){
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToSetting: () -> Unit,
    navigateToRandom: () -> Unit,
    navigateToRecode: () -> Unit,
    navigateToStatistic: () -> Unit,
    modifier: Modifier
){
    composable(
        route = homeRoute,
//        exitTransition = { slideToLeftExit() },
//        popEnterTransition = { slideToRightEnter() }
    ) {
        HomeRoute(
            navigateToSetting,
            navigateToRandom,
            navigateToRecode,
            navigateToStatistic,
            modifier
        )
    }
}