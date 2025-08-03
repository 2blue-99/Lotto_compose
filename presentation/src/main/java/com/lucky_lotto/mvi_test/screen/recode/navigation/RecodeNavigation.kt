package com.lucky_lotto.mvi_test.screen.recode.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.lucky_lotto.mvi_test.screen.recode.screen.RecodeRoute

const val recodeRoute = "recode"
fun NavController.navigateToRecode(navOptions: NavOptions? = null) {
    this.navigate(recodeRoute, navOptions)
}

fun NavGraphBuilder.recodeScreen(
    popBackStack: () -> Unit,
    modifier: Modifier
) {
    composable(
        route = recodeRoute,
    ) {
        RecodeRoute(
            popBackStack,
            modifier
        )
    }
}