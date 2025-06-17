package com.example.mvi_test.screen.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvi_test.screen.result.state.ResultEventState
import com.example.mvi_test.screen.result.state.ResultUIState

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = hiltViewModel()
) {
    val uiState = viewModel.resultUIState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("로또 추첨")
        }
        when(val state = uiState.value){
            is ResultUIState.Loading -> {
                item {
//                    Text("로딩중")
                    Button(
                        onClick = { viewModel.makeEvent(ResultEventState.OnMakeClick("")) }
                    ) {
                        Text("번호 추첨하기")
                    }
                }
            }
            is ResultUIState.Success -> {
                val list = state.result.number
                items(list.size) { index ->
                    Text("번호 $index : ${list[index]}")
                }
                item {
                    Button(
                        onClick = { viewModel.makeEvent(ResultEventState.OnRefresh) }
                    ) {
                        Text("확인")
                    }
                }
            }
            else -> {}
        }
    }
}

@Preview
@Composable
private fun ResultScreenPreview() {
    ResultScreen()
}