package com.example.mvi_test.screen.statistic.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.mvi_test.screen.recode.screen.RecodeScreen
import com.example.mvi_test.screen.statistic.screen.StatisticScreen

const val statisticRoute = "statistic"
fun NavController.navigateToStatistic(navOptions: NavOptions? = null){
    this.navigate(statisticRoute, navOptions)
}

fun NavGraphBuilder.statisticScreen(
    popBackStack: () -> Unit
){
    composable(
        route = statisticRoute,
//        exitTransition = { slideToLeftExit() },
//        popEnterTransition = { slideToRightEnter() }
    ) {
        StatisticScreen()
    }
}