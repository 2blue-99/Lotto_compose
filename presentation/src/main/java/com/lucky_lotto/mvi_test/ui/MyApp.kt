package com.lucky_lotto.mvi_test.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import com.lucky_lotto.mvi_test.designsystem.common.ForceUpdateDialog
import com.lucky_lotto.mvi_test.util.openStore
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lucky_lotto.domain.util.CommonMessage
import com.lucky_lotto.mvi_test.RequestUpdate
import com.lucky_lotto.mvi_test.designsystem.common.AdFinishDialog
import com.lucky_lotto.mvi_test.navigation.NavigationItem
import com.lucky_lotto.mvi_test.screen.home.navigation.homeScreen
import com.lucky_lotto.mvi_test.screen.keyword.navigation.keywordScreen
import com.lucky_lotto.mvi_test.screen.keyword.navigation.navigateToKeyword
import com.lucky_lotto.mvi_test.screen.qr.navigateToQR
import com.lucky_lotto.mvi_test.screen.qr.qrScannerScreen
import com.lucky_lotto.mvi_test.screen.recode.navigation.navigateToRecode
import com.lucky_lotto.mvi_test.screen.recode.navigation.recodeScreen
import com.lucky_lotto.mvi_test.screen.setting.navigation.navigateToSetting
import com.lucky_lotto.mvi_test.screen.setting.navigation.settingScreen
import com.lucky_lotto.mvi_test.screen.statistic.navigation.navigateToStatistic
import com.lucky_lotto.mvi_test.screen.statistic.navigation.statisticScreen
import com.lucky_lotto.mvi_test.ui.theme.ScreenBackground
import com.lucky_lotto.mvi_test.util.add.AdMobUtil
import com.lucky_lotto.mvi_test.util.add.AdOpeningUtil
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MyApp(
    activity: Activity,
    requestUpdate: RequestUpdate
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val addMobUtil = remember { AdMobUtil(activity) }
    val adOpeningUtil = remember { AdOpeningUtil(activity) }

    BackOnPressedFinish(activity = activity, adOpeningUtil = adOpeningUtil)

    if(requestUpdate.isUpdate) {
        ForceUpdateDialog(
            fetchNote = requestUpdate.fetchNote,
            onConfirm = { activity.openStore() }
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NavHostContainer(
                onShowSnackbar = {
                    snackbarHostState.showSnackbar(
                        message = it.message,
                    )
                },
                showFrontPageAd = addMobUtil::showFrontPageAd,
                showOpeningAd = {
                    adOpeningUtil.showAdIfAvailable(isRandom = true) { it() }
                },
                navController = navController,
                paddingValue = padding
            )
            // 하단 배너 광고
//            Box(
//                modifier = Modifier
//                    .padding(padding)
//                    .align(Alignment.BottomCenter),
//            ) {
//                CommonAdBanner(
//                    AdMobType.AdMobBottomBanner(
//                        context = LocalContext.current,
//                        width = LocalConfiguration.current.screenWidthDp
//                    )
//                )
//            }
        }
    }
}

@Composable
fun NavHostContainer(
    onShowSnackbar: suspend (CommonMessage) -> Unit,
    showFrontPageAd: () -> StateFlow<Boolean>,
    showOpeningAd: (onComplete: () -> Unit) -> Unit,
    navController: NavHostController,
    paddingValue: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ){
        homeScreen(
            navigateToQR = navController::navigateToQR,
            navigateToRandom = navController::navigateToKeyword,
            navigateToRecode = navController::navigateToRecode,
            navigateToSetting = navController::navigateToSetting,
            navigateToStatistic = navController::navigateToStatistic,
            modifier = Modifier.padding(paddingValue)
        )

        keywordScreen(
            onShowSnackbar = onShowSnackbar,
            showOpeningAd = showOpeningAd,
            modifier = Modifier.background(ScreenBackground).padding(paddingValue)
        )

        statisticScreen(
            showOpeningAd = showOpeningAd,
            modifier = Modifier.background(ScreenBackground).padding(paddingValue)
        )

        recodeScreen(
            navController::popBackStack,
            modifier = Modifier.background(ScreenBackground).padding(paddingValue)
        )

        settingScreen(
            modifier = Modifier.background(ScreenBackground).padding(paddingValue)
        )

        qrScannerScreen(
            navController::popBackStack,
            showFrontPageAd = showFrontPageAd,
            modifier = Modifier.background(ScreenBackground).padding(paddingValue)
        )
    }
}

@Composable
fun BackOnPressedFinish(activity: Activity, adOpeningUtil: AdOpeningUtil) {
    var dialogVisibleState by remember { mutableStateOf(false) }

    if (dialogVisibleState) {
        AdFinishDialog(
            onDismiss = { },
            onConfirm = { dialogVisibleState = false }, // 아니오 (유지)
            onCancel = { // 네 (종료)
                dialogVisibleState = false
                adOpeningUtil.showAdIfAvailable {
                    activity.finish()
                }
            }
        )
    }

    BackHandler {
        dialogVisibleState = true
    }
}
