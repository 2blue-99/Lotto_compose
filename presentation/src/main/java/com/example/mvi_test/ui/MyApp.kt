package com.example.mvi_test.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mvi_test.navigation.NavigationItem
import com.example.mvi_test.screen.home.navigation.homeScreen
import com.example.mvi_test.screen.random.navigation.navigateToRandom
import com.example.mvi_test.screen.random.navigation.randomScreen
import com.example.mvi_test.screen.recode.navigation.recodeScreen
import com.example.mvi_test.screen.setting.navigation.navigateToSetting
import com.example.mvi_test.screen.setting.navigation.settingScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            NavHostContainer(
                navController = navController,
            )
        }
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
        modifier = Modifier.fillMaxWidth().padding(WindowInsets.navigationBars.asPaddingValues()),
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
//                ),
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun NavHostContainer(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route
    ){
        homeScreen(
            navigateToRandom = navController::navigateToRandom,
            navigateToSetting = navController::navigateToSetting,
        )

        randomScreen(navController::popBackStack)

        recodeScreen(navController::popBackStack)

        settingScreen(navController::popBackStack)
    }
}

