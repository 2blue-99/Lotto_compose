package com.lucky_lotto.mvi_test.screen.random.screen

import android.content.Context
import android.os.Vibrator
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.lucky_lotto.domain.model.Keyword
import com.lucky_lotto.domain.type.DrawType
import com.lucky_lotto.domain.type.DrawType.Companion.TYPE_LUCKY
import com.lucky_lotto.domain.util.CommonMessage
import com.lucky_lotto.domain.util.Constants.DRAW_COMPLETE_TIME
import com.lucky_lotto.domain.util.Constants.PADDING_VALUE_AD_BOX
import com.lucky_lotto.mvi_test.R
import com.lucky_lotto.mvi_test.designsystem.common.AutoSizeText
import com.lucky_lotto.mvi_test.designsystem.common.CommonAnimationButton
import com.lucky_lotto.mvi_test.designsystem.common.CommonDrawResultContent
import com.lucky_lotto.mvi_test.designsystem.common.CommonExpandableBox
import com.lucky_lotto.mvi_test.designsystem.common.CommonFlowRow
import com.lucky_lotto.mvi_test.designsystem.common.CommonLazyRow
import com.lucky_lotto.mvi_test.designsystem.common.HorizontalSpacer
import com.lucky_lotto.mvi_test.designsystem.common.VerticalSpacer
import com.lucky_lotto.mvi_test.screen.random.RandomViewModel
import com.lucky_lotto.mvi_test.screen.random.state.LottoUIState
import com.lucky_lotto.mvi_test.screen.random.state.RandomActionState
import com.lucky_lotto.mvi_test.screen.random.state.RandomEffectState
import com.lucky_lotto.mvi_test.screen.random.state.TitleKeywordUIState
import com.lucky_lotto.mvi_test.ui.theme.CommonStyle
import com.lucky_lotto.mvi_test.ui.theme.LightGray
import com.lucky_lotto.mvi_test.ui.theme.ScreenBackground
import com.lucky_lotto.mvi_test.ui.theme.SubColor
import com.lucky_lotto.mvi_test.util.Utils.containsKeyword
import com.lucky_lotto.mvi_test.util.Utils.toKeyword
import com.lucky_lotto.mvi_test.util.startVibrate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RandomRoute(
    onShowSnackbar: suspend (CommonMessage) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: RandomViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val titleKeywordUIState by viewModel.titleKeywordUIState.collectAsStateWithLifecycle()
    val lottoUIState by viewModel.lottoUIState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffectState.collectLatest { effect ->
            when(effect){
                is RandomEffectState.ShowToast -> { Toast.makeText(context, effect.message.message, Toast.LENGTH_SHORT).show() }
                is RandomEffectState.ShowSnackbar -> { onShowSnackbar(effect.message) }
            }
        }
    }

    RandomScreen(
        titleKeywordUIState = titleKeywordUIState,
        lottoUIState = lottoUIState,
        actionHandler = viewModel::actionHandler,
        effectHandler = viewModel::effectHandler,
        modifier = modifier
    )
}

@Composable
fun RandomScreen(
    titleKeywordUIState: TitleKeywordUIState = TitleKeywordUIState.Loading,
    lottoUIState: LottoUIState = LottoUIState.Loading,
    actionHandler: (RandomActionState) -> Unit = {},
    effectHandler: (RandomEffectState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // 헹운 키워드 - 아이템 저장 시 포함시키기 위해 호스팅
    var keyword by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = PADDING_VALUE_AD_BOX.dp)
    ) {
        item {
            val isFirst = if(titleKeywordUIState is TitleKeywordUIState.Success) titleKeywordUIState.isFirst else false
            CommonExpandableBox(
                expand = isFirst,
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
                keyword = keyword,
                keywordList = if(titleKeywordUIState is TitleKeywordUIState.Success) titleKeywordUIState.keywordList else emptyList(),
                onChangeKeyword = { keyword = it },
                actionHandler = actionHandler,
                effectHandler = effectHandler,
            )
        }
        item {
            CommonDrawResultContent(
                onClickSave = { list ->
                    actionHandler(RandomActionState.OnClickSave(
                        drawKeyword = keyword,
                        list = list
                    ))
                },
                lottoList = if(lottoUIState is LottoUIState.Success) lottoUIState.lottoList else emptyList(),
                drawType = DrawType.LuckyDraw(keyword = keyword),
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
        titleKeywordUIState = TitleKeywordUIState.Loading
    )
}

@Composable
fun KeywordContent(
    keyword: String = TYPE_LUCKY, // 행운 키워드
    keywordList: List<Keyword> = listOf(Keyword(), Keyword(), Keyword(), Keyword(), Keyword()),
    onChangeKeyword: (String) -> Unit = {},
    actionHandler: (RandomActionState) -> Unit = {},
    effectHandler: (RandomEffectState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var expand by rememberSaveable { mutableStateOf(false) }
    // 추첨하기 클릭 가능 여부
    var drawClickable by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val rememberCoroutineScope = rememberCoroutineScope()
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

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
                textStyle = CommonStyle.text20Bold.copy(textAlign = TextAlign.Start),
                value = keyword,
                onValueChange = onChangeKeyword,
                placeholder = {
                    AutoSizeText(
                        text = "행운의 키워드를 입력해주세요.",
                        style = CommonStyle.text16Bold,
                        color = LightGray,
                        minSize = 10
                    )
                },
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

            CommonAnimationButton(
                modifier = Modifier
                    .weight(3f)
                    .height(50.dp),
                enableColor = SubColor,
                disableColor = LightGray,
                enabled = drawClickable,
                onClick = {
                    // Empty 체크
                    if(keyword.isNotBlank()){
                        context.startVibrate()
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
                            effectHandler.invoke(RandomEffectState.ShowToast(CommonMessage.RANDOM_EMPTY_KEYWORD))
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
                            onChangeKeyword(it)
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
                        onChangeKeyword(it)
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