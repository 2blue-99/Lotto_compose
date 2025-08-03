package com.lucky_lotto.mvi_test.screen.random.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.lucky_lotto.domain.util.CommonMessage
import com.lucky_lotto.mvi_test.screen.random.screen.RandomRoute

const val randomRoute = "random"
fun NavController.navigateToRandom(navOptions: NavOptions? = null){
    this.navigate(randomRoute, navOptions)
}

fun NavGraphBuilder.randomScreen(
    onShowSnackbar: suspend (CommonMessage) -> Unit,
    modifier: Modifier,
){
    composable(
        route = randomRoute,
    ) {
        RandomRoute(
            onShowSnackbar,
            modifier
        )
    }
}