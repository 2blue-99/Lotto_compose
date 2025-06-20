package com.example.mvi_test.screen.random.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.mvi_test.screen.random.screen.RandomScreen
import timber.log.Timber

const val randomRoute = "random"
fun NavController.navigateToRandom(navOptions: NavOptions? = null){
    this.navigate(randomRoute, navOptions)
}

fun NavGraphBuilder.randomScreen(
    popBackStack: () -> Unit
){
    composable(
        route = randomRoute,
//        exitTransition = { slideToLeftExit() },
//        popEnterTransition = { slideToRightEnter() }
    ) {
        RandomScreen(popBackStack)
    }
}