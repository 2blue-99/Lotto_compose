package com.example.mvi_test.screen.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import timber.log.Timber

@Composable
fun HomeScreen(
    navigateToRandom: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Timber.d("homeScreen")
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = navigateToSetting
        ) {
            Icon(imageVector = Icons.Default.Settings, null)
        }

        Button(
            onClick = navigateToRandom
        ) {
            Text("번호 추첨하기")
        }
    }
}

@Preview
@Composable
fun  HomeScreenPreview(){
    HomeScreen()
}