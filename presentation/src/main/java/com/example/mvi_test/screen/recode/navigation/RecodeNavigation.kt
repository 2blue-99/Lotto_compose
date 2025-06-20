package com.example.mvi_test.screen.recode.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.mvi_test.screen.recode.screen.RecodeScreen
import timber.log.Timber

const val recodeRoute = "recode"
fun NavController.navigateToRecode(navOptions: NavOptions? = null){
    this.navigate(recodeRoute, navOptions)
}

fun NavGraphBuilder.recodeScreen(
    popBackStack: () -> Unit
){
    composable(
        route = recodeRoute,
//        exitTransition = { slideToLeftExit() },
//        popEnterTransition = { slideToRightEnter() }
    ) {
        RecodeScreen(popBackStack)
    }
}