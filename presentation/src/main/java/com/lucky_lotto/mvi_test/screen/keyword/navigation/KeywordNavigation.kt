package com.lucky_lotto.mvi_test.screen.keyword.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.lucky_lotto.domain.util.CommonMessage
import com.lucky_lotto.mvi_test.screen.keyword.screen.KeywordRoute

const val keywordRoute = "keyword"
fun NavController.navigateToKeyword(navOptions: NavOptions? = null){
    this.navigate(keywordRoute, navOptions)
}

fun NavGraphBuilder.keywordScreen(
    onShowSnackbar: suspend (CommonMessage) -> Unit,
    modifier: Modifier,
){
    composable(
        route = keywordRoute,
    ) {
        KeywordRoute(
            onShowSnackbar,
            modifier
        )
    }
}