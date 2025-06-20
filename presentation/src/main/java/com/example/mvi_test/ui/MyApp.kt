package com.example.mvi_test.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mvi_test.navigation.NavigationItem
import com.example.mvi_test.screen.random.navigation.homeScreen
import com.example.mvi_test.screen.random.navigation.navigateToRandom
import com.example.mvi_test.screen.setting.navigation.navigateToSetting
import com.example.mvi_test.screen.random.navigation.randomScreen
import com.example.mvi_test.screen.recode.navigation.recodeScreen
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

    NavigationBar(
        contentColor = Color.DarkGray,
        containerColor = Color.LightGray,

    ) {
        destination.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(imageVector = if(isSelected) item.fillIcon else item.outLineIcon, contentDescription = "test") },
                label = { Text(stringResource(item.title)) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )

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

