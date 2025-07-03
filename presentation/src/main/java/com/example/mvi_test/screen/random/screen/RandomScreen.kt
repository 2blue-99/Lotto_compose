package com.example.mvi_test.screen.random.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvi_test.screen.random.RandomViewModel
import com.example.mvi_test.screen.random.state.RandomActionState
import com.example.mvi_test.screen.random.state.RandomUIState
import com.example.mvi_test.designsystem.common.CommonExpandableBox
import com.example.mvi_test.designsystem.common.CommonListItem
import com.example.mvi_test.designsystem.common.CommonLottoAutoScrollRow
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.util.CommonUtil.toAlphabet

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
            .background(Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            CommonExpandableBox(
                shrinkContent = {
                    Text(
                        text = "테스트 문구",
                        style = CommonStyle.text20,
                        color = Color.White
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "테스트 문구",
                        style = CommonStyle.text20,
                        color = Color.White
                    )
                },
                expandContent = {
                    Text(
                        text = "내가 보여요",
                        style = CommonStyle.text30,
                        color = Color.White
                    )
                }
            )
        }
        item {
            KeywordContent()
        }
        item {
            RandomResultContent()
        }
    }
}

@Preview
@Composable
private fun RandomScreenPreview() {
    RandomScreen(
        uiState = RandomUIState.Loading
    )
}

@Composable
fun KeywordContent(
    searchList: List<String> = listOf("aaaaaaa","bbbbbbb","cccccccc","ddddddd"),
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    var expand by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(7f)
                    .onFocusEvent {
                        expand = it.isFocused == true
                    },
                value = text,
                onValueChange = { text = it },
                label = { Text("test test") },
                placeholder = { Text("hint") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                )
            )

            Button(
                modifier = Modifier.weight(3f),
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            ) {
                Text("추첨하기")
            }
        }

        AnimatedVisibility(expand) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TODO 검색 개수가 5개 미만일 경우 대응 필요
                searchList.forEach { title ->
                    CommonListItem(
                        title = title,
                        onClick = {
                            text = it
                            expand = false
                            focusManager.clearFocus()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun KeywordBoxPreview() {
    KeywordContent()
}

@Composable
fun RandomResultContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(2.dp, Color.LightGray, RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            repeat(5) {
                RandomListItem(
                    index = it
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {}
                ) {
                    Text("복사하기")
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {}
                ) {
                    Text("공유하기")
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {}
                ) {
                    Text("웹으로 가기")
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {}
                ) {
                    Text("저장하기")
                }
            }
        }
    }
}

@Preview
@Composable
private fun RandomResultPreview() {
    RandomResultContent()
}

@Composable
fun RandomListItem(
    index: Int = 0,
    targetList: List<List<Int>> = emptyList(),
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(6.dp),
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
        ) {
            Checkbox(
                true, null
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = index.toAlphabet(),
                style = CommonStyle.text12
            )
        }
        Box(
            modifier = Modifier
                .weight(8f)
        ) {
            CommonLottoAutoScrollRow(
                targetList = (1..45).shuffled().take(7).sorted() // TODO 테스트용
            )
        }
    }
}

@Preview
@Composable
private fun RandomListItemPreview() {
    RandomListItem(
        targetList = listOf(
            listOf(1, 5, 10, 11, 20, 30, 36),
            listOf(2, 6, 12, 17, 21, 35, 41),
            listOf(3, 7, 13, 18, 22, 33, 44),
            listOf(4, 8, 14, 19, 23, 29, 39),
            listOf(9, 15, 24, 28, 31, 34, 45)
        )
    )
}





