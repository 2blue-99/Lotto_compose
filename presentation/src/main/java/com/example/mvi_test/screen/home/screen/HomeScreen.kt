package com.example.mvi_test.screen.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvi_test.screen.home.HomeViewModel
import com.example.mvi_test.screen.home.state.HomeActionState
import com.example.mvi_test.screen.home.state.HomeUIState
import com.example.mvi_test.ui.common.CommonAdBanner
import com.example.mvi_test.ui.theme.CommonStyle
import timber.log.Timber

@Composable
fun HomeScreen(
    navigateToRandom: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
){
    val uiState by viewModel.lottoState.collectAsStateWithLifecycle()

    HomeScreen(
        navigateToRandom = navigateToRandom,
        navigateToSetting = navigateToSetting,
        uiState = uiState,
        intentHandler = viewModel::intentHandler,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    navigateToRandom: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    uiState: HomeUIState = HomeUIState.Loading,
    intentHandler: (HomeActionState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    when(uiState){
        is HomeUIState.Loading -> Timber.d("Loading")
        is HomeUIState.Success -> Timber.d("Success : ${uiState.lotto}")
        else -> {}
    }


    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
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
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            CommonAdBanner()
        }
    }
}

@Preview
@Composable
fun  HomeScreenPreview(){
    HomeScreen(
        uiState = HomeUIState.Loading,
        intentHandler = {}
    )
}