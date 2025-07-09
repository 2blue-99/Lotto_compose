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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import com.example.domain.model.LottoItem
import com.example.domain.util.CommonMessage
import com.example.mvi_test.R
import com.example.mvi_test.designsystem.common.CommonButton
import com.example.mvi_test.designsystem.common.CommonExpandableBox
import com.example.mvi_test.designsystem.common.CommonFlowRow
import com.example.mvi_test.designsystem.common.CommonLazyRow
import com.example.mvi_test.designsystem.common.CommonLottoAutoRow
import com.example.mvi_test.designsystem.common.HorizontalSpacer
import com.example.mvi_test.designsystem.common.VerticalSpacer
import com.example.mvi_test.screen.random.RandomViewModel
import com.example.mvi_test.screen.random.state.KeywordUIState
import com.example.mvi_test.screen.random.state.LottoUIState
import com.example.mvi_test.screen.random.state.RandomActionState
import com.example.mvi_test.screen.random.state.RandomEffectState
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.LightGray
import com.example.mvi_test.ui.theme.ScreenBackground
import com.example.mvi_test.ui.theme.SubColor
import com.example.mvi_test.util.Utils.containsKeyword
import com.example.mvi_test.util.Utils.setAllFalse
import com.example.mvi_test.util.Utils.setAllTrue
import com.example.mvi_test.util.Utils.testLottoItem
import com.example.mvi_test.util.Utils.testLottoList
import com.example.mvi_test.util.Utils.toAlphabet
import com.example.mvi_test.util.Utils.toKeyword
import com.example.mvi_test.util.DRAW_COMPLETE_TIME
import com.example.mvi_test.util.DRAW_ITEM_SHOW_TIME
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
                actionHandler = actionHandler,
                effectHandler = effectHandler,
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
    var drawState by remember { mutableStateOf(true) }

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

    LaunchedEffect(drawState) {
        if(!drawState){
            // 로또 추첨 결과가 완료되는 시간 : 얼추 1.8초
            delay(DRAW_COMPLETE_TIME)
            drawState = true
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

            CommonButton(
                modifier = Modifier
                    .weight(3f)
                    .height(50.dp),
                enableColor = SubColor,
                disableColor = LightGray,
                enabled = drawState,
                onClick = {
                    // Empty 체크
                    if(text.isNotBlank()){
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        expand = false
                        drawState = false
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
    actionHandler: (RandomActionState) -> Unit = {},
    effectHandler: (RandomEffectState) -> Unit = {},
    lottoList: List<LottoItem> = testLottoList(),
    modifier: Modifier = Modifier
) {
    val itemList = remember { mutableStateListOf<Int>() }
    val checkBoxStates = remember { mutableStateListOf(false,false,false,false,false) }
    // 하단 버튼(저장, 복사, 공유) 활성화/비활성화 여부
    var isDrawCompleted by remember { mutableStateOf(false) }
    // 저장하기 1번이상 클릭 여부
    var isSaved by remember { mutableStateOf(false) }

    LaunchedEffect(lottoList) {
        if(lottoList.isNotEmpty()) {
            // 추첨 결과 아이템 순차 노출
            launch {
                // 기존의 추첨 결과 초기화
                itemList.clear()
                // 기존의 체크박스 상태 초기화
                repeat(5) { index ->
                    delay(DRAW_ITEM_SHOW_TIME)
                    itemList.add(index)
                }
            }
            // 전체 로또 노출 완료 여부 - 로또 아이템 체크박스 활성화
            launch {
                checkBoxStates.setAllFalse()
                delay(DRAW_COMPLETE_TIME)
                checkBoxStates.setAllTrue()
            }

            // 전체 로또 노출 완료 여부 - 하단 버튼 활성화
            launch {
                isDrawCompleted = false
                delay(DRAW_COMPLETE_TIME)
                isDrawCompleted = true
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

            itemList.forEach { index ->
                RandomListItem(
                    targetList = lottoList[index],
                    checkBox = checkBoxStates[index],
                    onCheckChange = {
                        checkBoxStates[index] = it
                    },
                    index = index
                )
                if(index < itemList.lastIndex){
                    HorizontalDivider(color = Color.LightGray)
                }
            }
        }

        // 로또가 비어있을 때
        AnimatedVisibility(
            visible = lottoList.isEmpty(),
            modifier = Modifier.matchParentSize()
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "추첨하기 버튼을 눌러주세요.",
                    style = CommonStyle.text20Bold,
                    color = Color.LightGray
                )
            }
        }
    }

    VerticalSpacer(10.dp)

    val saveCount = checkBoxStates.toList().count { it }
    val saveEnabled = if(isDrawCompleted && saveCount != 0 && !isSaved) true else false
    val saveText = if(!isDrawCompleted || isSaved) "저장하기" else "${saveCount}개 저장하기"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CommonButton(
            modifier = Modifier
                .weight(2f)
                .height(50.dp),
            enableColor = SubColor,
            disableColor = LightGray,
            enabled = saveEnabled,
            onClick = {
                val checkedItemList = checkBoxStates.toList().indices.mapIndexedNotNull { index, _ ->
                    if(checkBoxStates[index]) lottoList[index] else null
                }
                actionHandler(RandomActionState.OnClickSave(checkedItemList))
                effectHandler(RandomEffectState.ShowToast(CommonMessage.RANDOM_SAVED_SUCCESS))
                isSaved = true
            },
            text = saveText // 저장하기
        )
        CommonButton(
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            enableColor = DarkGray,
            disableColor = LightGray,
            enabled = isDrawCompleted,
            onClick = {},
            text = "복사하기"
        )
        CommonButton(
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            enableColor = DarkGray,
            disableColor = LightGray,
            enabled = isDrawCompleted,
            onClick = {},
            text = "공유하기"
        )
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
    checkBox: Boolean = false,
    onCheckChange: (Boolean) -> Unit = {},
    targetList: LottoItem = testLottoItem(),
    modifier: Modifier = Modifier
) {
    // 아이템 생성 시 페이드 아웃 -> 인
    val alpha = remember { Animatable(0f) }
    // targetList 를 CommonLottoAutoRow 에 바로 넣을 경우, 결과가 살짝 보이는 이슈 존재 -> 상태로 관리
    val lottoList by remember { mutableStateOf(targetList) }

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
                checked = checkBox,
                onCheckedChange = onCheckChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = DarkGray,
                    uncheckedColor = DarkGray,
                )
            )
        }
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = index.toAlphabet(),
                style = CommonStyle.text16,
                color = DarkGray
            )
        }
        Box(
            modifier = Modifier
                .weight(8f)
        ) {
            CommonLottoAutoRow(
                lottoItem = lottoList
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




