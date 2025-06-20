package com.example.mvi_test.screen.recode.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import timber.log.Timber

@Composable
fun RecodeScreen(
    popBackStack: () -> Unit
) {
    Timber.d("RecodeScreen")
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        Text("Recode")
    }
}