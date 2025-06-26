package com.example.mvi_test.screen.random.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvi_test.screen.random.RandomViewModel
import com.example.mvi_test.screen.random.state.RandomActionState
import com.example.mvi_test.screen.random.state.RandomUIState
import com.example.mvi_test.ui.common.CommonExpendableBox
import com.example.mvi_test.ui.theme.CommonStyle

@Composable
fun RandomScreen(
    popBackStack: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: RandomViewModel = hiltViewModel()
) {
    val uiState by viewModel.randomUIState.collectAsStateWithLifecycle()

    RandomScreen(
        popBackStack = popBackStack,
        uiState = uiState,
        intentHandler = viewModel::intentHandler,
        modifier = modifier
    )
}

@Composable
fun RandomScreen(
    popBackStack: () -> Unit = {},
    uiState: RandomUIState = RandomUIState.Loading,
    intentHandler: (RandomActionState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(modifier = Modifier.padding(10.dp)) {
                CommonExpendableBox(
                    shrinkContent = {
                        Text(
                            text = "테스트 문구",
                            style = CommonStyle.text20
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = "테스트 문구",
                            style = CommonStyle.text20
                        )
                    },
                    expandContent = {
                            Text(
                                text = "내가 보여요",
                                style = CommonStyle.text30
                            )
                    }
                )
            }
        }
//        when(val state = uiState.value){
//            is RandomUIState.Loading -> {
//                item {
//                    Button(
//                        onClick = { viewModel.makeEvent(RandomEventState.OnMakeClick("")) }
//                    ) {
//                        Text("번호 추첨하기")
//                    }
//                }
//            }
//            is RandomUIState.Success -> {
//                val list = state.result.number
//                items(list.size) { index ->
//                    Text("번호 $index : ${list[index]}")
//                }
//                item {
//                    Button(
//                        onClick = { viewModel.makeEvent(RandomEventState.OnRefresh) }
//                    ) {
//                        Text("확인")
//                    }
//                }
//            }
//            else -> {}
//        }
    }
}

@Preview
@Composable
private fun RandomScreenPreview() {
    RandomScreen(
        uiState = RandomUIState.Loading
    )
}