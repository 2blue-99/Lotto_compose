package com.example.mvi_test.screen.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import timber.log.Timber

@Composable
fun SettingScreen(
    popBackStack: () -> Unit
) {
    Timber.d("settingScreen")
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        Text("Setting")
    }
}