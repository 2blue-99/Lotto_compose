package com.example.mvi_test.screen.random.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.domain.util.CommonMessage
import com.example.mvi_test.screen.random.screen.RandomRoute

const val randomRoute = "random"
fun NavController.navigateToRandom(navOptions: NavOptions? = null){
    this.navigate(randomRoute, navOptions)
}

fun NavGraphBuilder.randomScreen(
    onShowSnackbar: suspend (CommonMessage) -> Unit,
    popBackStack: () -> Unit,
    modifier: Modifier,
){
    composable(
        route = randomRoute,
//        exitTransition = { slideToLeftExit() },
//        popEnterTransition = { slideToRightEnter() }
    ) {
        RandomRoute(
            onShowSnackbar,
            popBackStack,
            modifier
        )
    }
}