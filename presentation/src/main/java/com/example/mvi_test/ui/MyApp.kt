package com.example.mvi_test.ui

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.domain.util.CommonMessage
import com.example.mvi_test.R
import com.example.mvi_test.navigation.NavigationItem
import com.example.mvi_test.screen.home.navigation.homeScreen
import com.example.mvi_test.screen.random.navigation.navigateToRandom
import com.example.mvi_test.screen.random.navigation.randomScreen
import com.example.mvi_test.screen.recode.navigation.navigateToRecode
import com.example.mvi_test.screen.recode.navigation.recodeScreen
import com.example.mvi_test.screen.setting.navigation.navigateToSetting
import com.example.mvi_test.screen.setting.navigation.settingScreen
import com.example.mvi_test.screen.statistic.navigation.navigateToStatistic
import com.example.mvi_test.screen.statistic.navigation.statisticScreen
import com.example.mvi_test.ui.theme.ScreenBackground

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    BackOnPressed(navController)

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
//        bottomBar = {
//            BottomNavigationBar(navController)
//        }
    ) { padding ->
        NavHostContainer(
            onShowSnackbar = {
                snackbarHostState.showSnackbar(
                    message = it.message,
                )
            },
            navController = navController,
            paddingValue = padding
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val destination: List<NavigationItem> = NavigationItem.toList()

    BottomNavigation (
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        backgroundColor = Color.White,
        contentColor = Color(0xFF3F414E)
    ) {
        destination.forEachIndexed { index, item ->
            val isSelected = currentRoute == item.route
            BottomNavigationItem(
                modifier = Modifier.height(62.dp),
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true // 하나만 생성
                        restoreState = true // 버튼 재클릭 시 유지
                    }
                },
                icon = {
                    Icon(
                        imageVector = if(isSelected) item.fillIcon else item.outLineIcon,
                        contentDescription = "test"
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.title),
                        fontSize = 12.sp
                    )
                },
//                colors = NavigationBarItemDefaults.colors(
//                    indicatorColor = Color.Transparent
//                )
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun NavHostContainer(
    onShowSnackbar: suspend (CommonMessage) -> Unit,
    navController: NavHostController,
    paddingValue: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route,
    ){
        homeScreen(
            navigateToRandom = navController::navigateToRandom,
            navigateToRecode = navController::navigateToRecode,
            navigateToSetting = navController::navigateToSetting,
            navigateToStatistic = navController::navigateToStatistic,
            modifier = Modifier.padding(paddingValue)
        )

        randomScreen(
            onShowSnackbar = onShowSnackbar,
            popBackStack = navController::popBackStack,
            modifier = Modifier.background(ScreenBackground).padding(paddingValue)
        )

        statisticScreen(
            popBackStack = {},
            modifier = Modifier.background(ScreenBackground).padding(paddingValue)
        )

        recodeScreen(
            navController::popBackStack,
            modifier = Modifier.background(ScreenBackground).padding(paddingValue)
        )

        settingScreen(
            navController::popBackStack,
            modifier = Modifier.background(ScreenBackground).padding(paddingValue)
        )
    }
}

@Composable
fun BackOnPressed(
    navController: NavHostController
) {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L
    val backNoticeMessage = stringResource(R.string.app_back_notice)
    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 1000L) {
            (context as Activity).finish()
        } else {
            backPressedState = true
            Toast.makeText(context, backNoticeMessage, Toast.LENGTH_SHORT).show()
            backPressedTime = System.currentTimeMillis()
        }
    }
}
