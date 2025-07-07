package com.example.mvi_test.screen.recode.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvi_test.screen.recode.RecodeViewModel
import com.example.mvi_test.screen.recode.state.RecodeUIState

@Composable
fun RecodeScreen(
    popBackStack: () -> Unit = {},
    viewModel: RecodeViewModel = hiltViewModel()
) {
    val recodeUIState by viewModel.recodeUIState.collectAsStateWithLifecycle()

    RecodeScreen(
        recodeUIState = recodeUIState
    )
}

@Composable
fun RecodeScreen(
    recodeUIState: RecodeUIState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("$recodeUIState")
    }
}