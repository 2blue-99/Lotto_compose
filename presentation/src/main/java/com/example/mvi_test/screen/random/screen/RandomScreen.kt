package com.example.mvi_test.screen.random.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.mvi_test.designsystem.common.CommonButton
import com.example.mvi_test.designsystem.common.CommonDrawResultContent
import com.example.mvi_test.designsystem.common.CommonExpandableBox
import com.example.mvi_test.designsystem.common.CommonFlowRow
import com.example.mvi_test.designsystem.common.CommonLazyRow
import com.example.mvi_test.designsystem.common.HorizontalSpacer
import com.example.mvi_test.designsystem.common.VerticalSpacer
import com.example.mvi_test.screen.random.RandomViewModel
import com.example.mvi_test.screen.random.state.KeywordUIState
import com.example.mvi_test.screen.random.state.LottoUIState
import com.example.mvi_test.screen.random.state.RandomActionState
import com.example.mvi_test.screen.random.state.RandomEffectState
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.LightGray
import com.example.mvi_test.ui.theme.ScreenBackground
import com.example.mvi_test.ui.theme.SubColor
import com.example.mvi_test.util.DRAW_COMPLETE_TIME
import com.example.mvi_test.util.Utils.containsKeyword
import com.example.mvi_test.util.Utils.toKeyword
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RandomRoute(
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
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 50.dp)
    ) {
        item {
            CommonExpandableBox(
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
            CommonDrawResultContent(
                onClickSave = { list ->
                    actionHandler(RandomActionState.OnClickSave(list))
                    effectHandler(RandomEffectState.ShowToast(CommonMessage.RANDOM_SAVED_SUCCESS))
                },
                lottoList = if(lottoUIState is LottoUIState.Success) lottoUIState.lottoList else emptyList(),
                mainColor = SubColor
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
    var keyword by remember { mutableStateOf("") }
    var expand by remember { mutableStateOf(false) }
    // 추첨하기 클릭 가능 여부
    var drawClickable by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val rememberCoroutineScope = rememberCoroutineScope()
//    val drawButtonColor by animateColorAsState(
//        targetValue = if(drawState) SubColor else LightGray,
//        animationSpec = tween(durationMillis = 500),
//        label = "buttonColor"
//    )

    // 사용성을 높이기 위해 화면 진입 후(0.2초) 검색창 보여주기
    LaunchedEffect(Unit) {
        delay(200)
        expand = true
    }

    LaunchedEffect(drawClickable) {
        if(!drawClickable){
            // 로또 추첨 결과가 완료되는 시간 : 얼추 1.8초
            delay(DRAW_COMPLETE_TIME)
            drawClickable = true
        }
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
                value = keyword,
                onValueChange = { keyword = it },
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

            CommonButton(
                modifier = Modifier
                    .weight(3f)
                    .height(50.dp),
                enableColor = SubColor,
                disableColor = LightGray,
                enabled = drawClickable,
                onClick = {
                    // Empty 체크
                    if(keyword.isNotBlank()){
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        expand = false
                        drawClickable = false
                        rememberCoroutineScope.launch {
                            delay(100) // TODO 해당 딜레이가 없으면 부모 컴포넌트가 숨겨지기 전에 업데이트된게 보여서 추가한 임시방편
                            if(!keywordList.containsKeyword(keyword)){
                                actionHandler.invoke(RandomActionState.AddKeyword(keyword))
                            }
                            actionHandler.invoke(RandomActionState.OnClickDraw(keyword))
                        }
                    }else{
                        rememberCoroutineScope.launch {
                            effectHandler.invoke(RandomEffectState.ShowSnackbar(CommonMessage.RANDOM_EMPTY_KEYWORD))
                        }
                    }
                },
                text = "추첨하기"
            )
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
                            keyword = it
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
                        keyword = it
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