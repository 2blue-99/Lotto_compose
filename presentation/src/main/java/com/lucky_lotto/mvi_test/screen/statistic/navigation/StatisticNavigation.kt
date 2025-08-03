package com.lucky_lotto.mvi_test.screen.statistic.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.lucky_lotto.mvi_test.screen.statistic.screen.StatisticRoute

const val statisticRoute = "statistic"
fun NavController.navigateToStatistic(navOptions: NavOptions? = null){
    this.navigate(statisticRoute, navOptions)
}

fun NavGraphBuilder.statisticScreen(
    modifier: Modifier,
    ){
    composable(
        route = statisticRoute,
    ) {
        StatisticRoute(
            modifier
        )
    }
}