package com.example.mvi_test.screen.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.mvi_test.screen.home.screen.HomeScreen

const val homeRoute = "home"
fun NavController.navigateToHome(navOptions: NavOptions? = null){
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToRandom: () -> Unit,
    navigateToSetting: () -> Unit
){
    composable(
        route = homeRoute,
//        exitTransition = { slideToLeftExit() },
//        popEnterTransition = { slideToRightEnter() }
    ) {
        HomeScreen(
            navigateToRandom,
            navigateToSetting
        )
    }
}