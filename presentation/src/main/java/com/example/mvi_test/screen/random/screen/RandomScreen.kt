package com.example.mvi_test.screen.random.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.Keyword
import com.example.domain.util.CommonMessage
import com.example.mvi_test.R
import com.example.mvi_test.designsystem.common.CommonExpandableBox
import com.example.mvi_test.designsystem.common.CommonFlowRow
import com.example.mvi_test.designsystem.common.CommonLazyRow
import com.example.mvi_test.designsystem.common.CommonLottoAutoRow
import com.example.mvi_test.designsystem.common.HorizontalSpacer
import com.example.mvi_test.designsystem.common.VerticalSpacer
import com.example.mvi_test.screen.random.RandomViewModel
import com.example.mvi_test.screen.random.state.KeywordUIState
import com.example.mvi_test.screen.random.state.RandomEffectState
import com.example.mvi_test.screen.random.state.RandomActionState
import com.example.mvi_test.screen.random.state.LottoUIState
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.ScreenBackground
import com.example.mvi_test.ui.theme.SubColor
import com.example.mvi_test.util.CommonUtil.containsKeyword
import com.example.mvi_test.util.CommonUtil.toAlphabet
import com.example.mvi_test.util.CommonUtil.toKeyword
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun RandomScreen(
    onShowSnackbar: suspend (CommonMessage) -> Unit = {},
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RandomViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keywordUIState by viewModel.keywordUIState.collectAsStateWithLifecycle()
    val lottoUIState by viewModel.lottoUIState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffectState.collectLatest { effect ->
            when(effect){
                is RandomEffectState.ShowToast -> { Toast.makeText(context, effect.message.message, Toast.LENGTH_SHORT).show() }
                is RandomEffectState.ShowSnackbar -> { onShowSnackbar(effect.message) }
                else -> {}
            }
        }
    }

    RandomScreen(
        popBackStack = popBackStack,
        keywordUIState = keywordUIState,
        lottoUIState = lottoUIState,
        actionHandler = viewModel::actionHandler,
        effectHandler = viewModel::effectHandler,
        modifier = modifier
    )
}

@Composable
fun RandomScreen(
    popBackStack: () -> Unit = {},
    keywordUIState: KeywordUIState = KeywordUIState.Loading,
    lottoUIState: LottoUIState = LottoUIState.Loading,
    actionHandler: (RandomActionState) -> Unit = {},
    effectHandler: (RandomEffectState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            CommonExpandableBox(
                showQuestion = true,
                shrinkContent = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "행운 로또 추첨",
                                style = CommonStyle.text24Bold,
                                color = Color.White
                            )
                        }
                    }
                },
                expandContent = {
                    Text(
                        text = stringResource(R.string.random_bar_explain),
                        style = CommonStyle.text14,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                }
            )
        }
        item {
            KeywordContent(
                actionHandler = actionHandler,
                effectHandler = effectHandler,
                keywordList = if(keywordUIState is KeywordUIState.Success) keywordUIState.keywordList else emptyList(),
            )
        }
        item {
            RandomResultContent(
                lottoList = if(lottoUIState is LottoUIState.Success) lottoUIState.lottoList else emptyList()
            )
        }
    }
}

@Preview
@Composable
private fun RandomScreenPreview() {
    RandomScreen(
        lottoUIState = LottoUIState.Loading,
        keywordUIState = KeywordUIState.Loading
    )
}

@Composable
fun KeywordContent(
    actionHandler: (RandomActionState) -> Unit = {},
    effectHandler: (RandomEffectState) -> Unit = {},
    keywordList: List<Keyword> = listOf(Keyword(), Keyword(), Keyword(), Keyword(), Keyword()),
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    var expand by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val rememberCoroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(200)
        expand = true
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                modifier = Modifier
                    .weight(7f)
                    .onFocusEvent {
                        if (it.isFocused) {
                            expand = true
                        }
                    },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedPlaceholderColor = Color.LightGray,
                    focusedPlaceholderColor  = Color.LightGray,
                    focusedTextColor = SubColor,
                    unfocusedTextColor = SubColor,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray
                ),
                textStyle = CommonStyle.text20.copy(textAlign = TextAlign.Start),
                value = text,
                onValueChange = { text = it },
                placeholder = { Text(
                    text = "행운의 키워드를 입력해주세요.",
                    style = CommonStyle.text16
                ) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
            )

            HorizontalSpacer(12.dp)

            Button(
                modifier = Modifier.weight(3f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SubColor),
                contentPadding = PaddingValues(4.dp),
                onClick = {
                    // Empty 체크
                    if(text.isNotBlank()){
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        expand = false
                        rememberCoroutineScope.launch {
                            delay(100) // TODO 해당 딜레이가 없으면 부모 컴포넌트가 숨겨지기 전에 업데이트된게 보여서 추가한 임시방편
                            if(!keywordList.containsKeyword(text)){
                                actionHandler.invoke(RandomActionState.AddKeyword(text))
                            }
                            actionHandler.invoke(RandomActionState.OnClickDraw(text))
                        }
                    }else{
                        rememberCoroutineScope.launch {
                            effectHandler.invoke(RandomEffectState.ShowSnackbar(CommonMessage.RANDOM_EMPTY_KEYWORD))
                        }
                    }
                },
            ) {
                Text(
                    text = "추첨하기",
                    style = CommonStyle.text16
                )
            }
        }

        AnimatedVisibility(expand) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                VerticalSpacer(16.dp)

                if(keywordList.isNotEmpty()){
                    Text(text = "최근", style = CommonStyle.text14Bold)

                    VerticalSpacer(4.dp)

                    CommonLazyRow(
                        keywordList = keywordList,
                        removable = true,
                        onClickChip = {
                            text = it
                            expand = false
                            focusManager.clearFocus()
                        },
                        onClickDelete = {
                            actionHandler.invoke(RandomActionState.DeleteKeyword(it))
                        }
                    )

                    VerticalSpacer(16.dp)
                }

                Text(text = "추천", style = CommonStyle.text14Bold)

                VerticalSpacer(4.dp)

                CommonFlowRow(
                    keywordList = context.resources.getStringArray(R.array.keyword_list).map { it.toKeyword() },
                    removable = false,
                    onClickChip = {
                        text = it
                        expand = false
                        focusManager.clearFocus()
                    }
                )
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
fun RandomResultContent(
    lottoList: List<List<Int>> = testInput(),
    modifier: Modifier = Modifier
) {
    val itemList = remember { mutableStateListOf<Int>() }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(lottoList) {
        if(lottoList.isEmpty()){
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800)
            )
        }else{
            launch {
                // Empty 문구 Hide
                alpha.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 800)
                )
            }

            itemList.clear()
            repeat(5) { index ->
                delay(300) // 300ms 간격으로 하나씩 추가
                itemList.add(index)
            }
        }
    }

    Box {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp)
                .heightIn(min = 200.dp)
                .animateContentSize()
        ) {
            Text(
                text = "추첨 결과",
                style = CommonStyle.text16Bold
            )

            VerticalSpacer(10.dp)

            itemList.forEach {
                RandomListItem(
                    targetList = lottoList[it],
                    index = it
                )
                if(it < itemList.lastIndex){
                    HorizontalDivider(color = Color.LightGray)
                }
            }
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(alpha.value),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "추첨하기 버튼을 눌러주세요.",
                style = CommonStyle.text20Bold,
                color = Color.LightGray
            )
        }
    }

//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.spacedBy(10.dp)
//    ) {
//        Button(
//            modifier = Modifier.weight(1f),
//            onClick = {}
//        ) {
//            Text("복사하기")
//        }
//        Button(
//            modifier = Modifier.weight(1f),
//            onClick = {}
//        ) {
//            Text("공유하기")
//        }
//        Button(
//            modifier = Modifier.weight(1f),
//            onClick = {}
//        ) {
//            Text("웹으로 가기")
//        }
//        Button(
//            modifier = Modifier.weight(1f),
//            onClick = {}
//        ) {
//            Text("저장하기")
//        }
//    }
}

@Preview
@Composable
private fun RandomResultPreview() {
    RandomResultContent()
}

@Composable
fun RandomListItem(
    index: Int = 0,
    targetList: List<Int> = (1..45).shuffled().take(7).sorted(),
    modifier: Modifier = Modifier
) {
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800)
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .alpha(alpha.value)
            .background(Color.White)
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
            CommonLottoAutoRow(
                targetList = targetList
            )
        }
    }
}

@Preview
@Composable
private fun RandomListItemPreview() {
    RandomListItem(
//        targetList = listOf(
//            listOf(1, 5, 10, 11, 20, 30, 36),
//            listOf(2, 6, 12, 17, 21, 35, 41),
//            listOf(3, 7, 13, 18, 22, 33, 44),
//            listOf(4, 8, 14, 19, 23, 29, 39),
//            listOf(9, 15, 24, 28, 31, 34, 45)
//        )
    )
}

fun testInput(): List<List<Int>> {
    return listOf(
            listOf(1, 5, 10, 11, 20, 30, 36),
            listOf(2, 6, 12, 17, 21, 35, 41),
            listOf(3, 7, 13, 18, 22, 33, 44),
            listOf(4, 8, 14, 19, 23, 29, 39),
            listOf(9, 15, 24, 28, 31, 34, 45)
        )
}





