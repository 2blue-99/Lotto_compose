package com.example.mvi_test.screen.random.navigation

import androidx.compose.material.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.domain.util.CommonMessage
import com.example.mvi_test.screen.random.screen.RandomScreen
import timber.log.Timber

const val randomRoute = "random"
fun NavController.navigateToRandom(navOptions: NavOptions? = null){
    this.navigate(randomRoute, navOptions)
}

fun NavGraphBuilder.randomScreen(
    onShowSnackbar: suspend (CommonMessage) -> Unit,
    popBackStack: () -> Unit
){
    composable(
        route = randomRoute,
//        exitTransition = { slideToLeftExit() },
//        popEnterTransition = { slideToRightEnter() }
    ) {
        RandomScreen(
            onShowSnackbar,
            popBackStack
        )
    }
}