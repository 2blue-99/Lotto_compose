package com.example.mvi_test.screen.statistic.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.StatisticItem
import com.example.domain.type.RangeType
import com.example.domain.util.CommonMessage
import com.example.mvi_test.designsystem.common.CommonButton
import com.example.mvi_test.designsystem.common.CommonDrawResultContent
import com.example.mvi_test.designsystem.common.CommonExpandableBox
import com.example.mvi_test.designsystem.common.CommonLottoCircle
import com.example.mvi_test.designsystem.common.DynamicHorizontalSelector
import com.example.mvi_test.designsystem.common.HorizontalSpacer
import com.example.mvi_test.designsystem.common.VerticalSpacer
import com.example.mvi_test.screen.random.state.LottoUIState
import com.example.mvi_test.screen.statistic.StatisticViewModel
import com.example.mvi_test.screen.statistic.state.StatisticActionState
import com.example.mvi_test.screen.statistic.state.StatisticEffectState
import com.example.mvi_test.screen.statistic.state.StatisticUIState
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.LightGray
import com.example.mvi_test.ui.theme.PrimaryColor
import com.example.mvi_test.ui.theme.ScreenBackground
import com.example.mvi_test.util.DRAW_COMPLETE_TIME
import com.example.mvi_test.util.Utils.toLottoColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StatisticRoute(
    modifier: Modifier = Modifier,
    viewModel: StatisticViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val statisticUIState by viewModel.statisticUIState.collectAsStateWithLifecycle()
    val lottoUIState by viewModel.lottoUIState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffectState.collectLatest { effect ->
            when(effect){
                is StatisticEffectState.ShowToast -> { Toast.makeText(context, effect.message.message, Toast.LENGTH_SHORT).show() }
                is StatisticEffectState.ShowSnackbar -> {  }
                else -> {}
            }
        }
    }

    StatisticScreen(
        statisticUIState = statisticUIState,
        lottoUIState = lottoUIState,
        actionHandler = viewModel::actionHandler,
        effectHandler = viewModel::effectHandler,
        modifier = modifier
    )
}

@Composable
fun StatisticScreen(
    statisticUIState: StatisticUIState = StatisticUIState.Loading,
    lottoUIState: LottoUIState = LottoUIState.Loading,
    actionHandler: (StatisticActionState) -> Unit,
    effectHandler: (StatisticEffectState) -> Unit,
    modifier: Modifier = Modifier
) {
    var rangeType by remember { mutableStateOf(RangeType.ONE_YEAR) } // 통계 조회 범위
    var expand by remember { mutableStateOf(false) }
    val selectNumberList = remember { mutableStateListOf<String>() } // 선택 로또 리스트
    val lazyState = rememberLazyListState()

    // 범위 변경 시 이벤트 발생
    LaunchedEffect(rangeType) {
        actionHandler(StatisticActionState.OnClickRange(rangeType))
    }

    // 범위 변경 시 선택 로또 초기화
    LaunchedEffect(statisticUIState) {
        selectNumberList.clear()
    }

    // 로또 추첨 완료 시, 화면 아래로 자동 스크롤
    LaunchedEffect(lottoUIState) {
        if(lottoUIState is LottoUIState.Success){
            delay(DRAW_COMPLETE_TIME)
            lazyState.animateScrollToItem(3)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        state = lazyState,
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
                                text = "통계 로또 추첨",
                                style = CommonStyle.text24Bold,
                                color = Color.White
                            )
                        }
                    }
                },
//                expandContent = {
//                    Text(
//                        text = stringResource(R.string.random_bar_explain),
//                        style = CommonStyle.text14,
//                        color = Color.White,
//                        textAlign = TextAlign.Center,
//                    )
//                }
            )
        }

        item {
            DynamicHorizontalSelector(
                actionHandler = actionHandler,
                currentRange = rangeType,
                onClickRange = { rangeType = it }
            )
        }
        item {
            StatisticContent(
                rangeType = rangeType,
                statisticUIState = statisticUIState,
                expand = expand,
                onChangeExpand = { expand = it },
                changeSelectState = { state, number ->
                    if (state) {
                        // 로또 선택 처리
                        selectNumberList.add(number)
                    } else {
                        // 로또 선택 해제
                        selectNumberList.remove(number)
                    }
                }
            )
        }

        item {
            SelectDrawContent(
                // 추첨하기 버튼 클릭
                onClickDraw = {
                    expand = false
                    actionHandler(StatisticActionState.OnClickDraw(selectNumberList.toList()))
                },
                selectList = selectNumberList.toList()
            )
        }

        item {
            CommonDrawResultContent(
                onClickSave = { list ->
                    actionHandler(StatisticActionState.OnClickSave(
                        // 필수 숫자 정렬 저장
                        requireNumber = selectNumberList.map(String::toInt).sorted()
                            .joinToString(", ", transform = Int::toString),
                        list = list
                    ))
                    effectHandler(StatisticEffectState.ShowToast(CommonMessage.RANDOM_SAVED_SUCCESS))
                },
                lottoList = if(lottoUIState is LottoUIState.Success) lottoUIState.lottoList else emptyList(),
                mainColor = PrimaryColor
            )
        }

        item {
//            HorizontalSpacer(30.dp)
        }
    }
}

@Preview
@Composable
private fun StatisticScreenPreview() {
    StatisticScreen(
        StatisticUIState.Loading,
        LottoUIState.Loading,
        {},
        {}
    )
}

@Composable
fun StatisticContent(
    rangeType: RangeType = RangeType.THREE_MONTH,
    statisticUIState: StatisticUIState = StatisticUIState.Loading,
    expand: Boolean = false,
    onChangeExpand: (Boolean) -> Unit = {},
    changeSelectState: (Boolean, String) -> Unit,
    modifier: Modifier = Modifier
) {
    // 최초 진입 확장 딜레이
    LaunchedEffect(rangeType) {
        delay(200)
        onChangeExpand(true)
    }

    when (statisticUIState) {
        is StatisticUIState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .animateContentSize()
                    .padding(16.dp)
            ) {

                val itemList = statisticUIState.statisticItem
                // 막대 그래프 비율 산정을 위한 가장 큰 값 변수화
                val higherCount = itemList.first().count.toInt().toFloat()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onChangeExpand(!expand)
                    }
                ) {
                    Text(
                        text = "최근 ${rangeType.monthText} 추첨 통계",
                        style = CommonStyle.text16Bold,
                        modifier = modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = DarkGray,
                        contentDescription = "expandable",
                    )
                }

                VerticalSpacer(10.dp)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // 기본 리스트
                    itemList.slice(0..2).forEachIndexed { index, item ->
                        StatisticItem(
                            item = item,
                            percentage = item.count.toInt() / higherCount,
                            onclickItem = changeSelectState
                        )
                    }

                    // 확장 리스트
                    AnimatedVisibility(
                        visible = expand,
                    ) {
                        Column {
                            itemList.slice(3..5).forEachIndexed { index, item ->
                                StatisticItem(
                                    item = item,
                                    percentage = item.count.toInt() / higherCount,
                                    onclickItem = changeSelectState
                                )
                            }
                        }
                    }
                }
            }
        }
        else -> StatisticEmpty() // 비었을 경우
    }
}

@Composable
fun StatisticEmpty(modifier: Modifier = Modifier) {
    // 리스트가 비어있는 경우
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "데이터를 불러올 수 없습니다..",
            style = CommonStyle.text18Bold,
            color = Color.LightGray
        )
    }
}

@Preview
@Composable
private fun StatisticContentPreview() {
    StatisticContent(
        changeSelectState = {b,s ->}
    )
}

@Composable
fun StatisticItem(
    item: StatisticItem,
    percentage: Float = 1f, // 0~1
    onclickItem: (Boolean, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gradientList = listOf(Color.White, item.number.toInt().toLottoColor())
    val shrinkGradient = Brush.linearGradient(colors = gradientList)
    var checked by remember { mutableStateOf(false) }

    // checked 초기화 처리
    LaunchedEffect(item) {
        checked = false
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box { // CheckBox 는 Box 로 싸매야 Preview 에 범위가 나옴
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    checked = !checked
                    onclickItem(checked, item.number)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = DarkGray,
                    uncheckedColor = DarkGray,
                ),
            )
        }

        CommonLottoCircle(
            targetNumber = item.number,
            isAnimation = false,
            modifier = Modifier.size(30.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(percentage) // TODO 여기다가 비율 넣으면 됨
                    .height(30.dp)
                    .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                    .background(shrinkGradient)
            )
        }

        Box(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            Text(
                text = item.count + "번",
                style = CommonStyle.text14
            )
        }
    }
}

@Composable
fun SelectDrawContent(
    onClickDraw: () -> Unit = {},
    selectList: List<String> = listOf("7","7","7"),
    modifier: Modifier = Modifier
) {
    // 추첨하기 클릭 가능 여부
    var drawClickable by remember { mutableStateOf(true) }

    LaunchedEffect(drawClickable) {
        if(!drawClickable){
            // 로또 추첨 결과가 완료되는 시간 : 얼추 1.8초
            delay(DRAW_COMPLETE_TIME)
            drawClickable = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "포함시킬 번호",
            style = CommonStyle.text16Bold
        )

        VerticalSpacer(10.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(7f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 존재하거나 빈 여부에 따라서 애니메이션 적용
                AnimatedContent(
                    targetState = selectList.isNotEmpty(),
                    label = "SelectListSwitch"
                ) { isNotEmpty ->
                    if(isNotEmpty){
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // 키를 걸어서 불필요한 애니메이션 방지
                            itemsIndexed(items = selectList, key = {index, item -> item}){ index, number ->
                                CommonLottoCircle(
                                    targetNumber = number,
                                    isAnimation = false,
                                    modifier = Modifier
                                        .animateItem()
                                        .size(30.dp)
                                )
                                if (index < selectList.lastIndex) {
                                    HorizontalSpacer(4.dp)
                                }
                            }
                        }
                    }else{
                        // 비었을 경우
                        SelectDrawEmpty()
                    }
                }

                VerticalSpacer(4.dp)

                HorizontalDivider(color =  LightGray)
            }

            HorizontalSpacer(8.dp)

            CommonButton(
                enableColor = PrimaryColor,
                enabled = drawClickable,
                modifier = Modifier
                    .weight(3f),
                onClick = {
                    drawClickable = false
                    onClickDraw()
                },
                text = "추첨하기",
            )
        }
    }
}

@Composable
fun SelectDrawEmpty(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.height(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "숫자를 선택해 주세요",
            style = CommonStyle.text16Bold,
            color = LightGray
        )
    }
}

@Preview
@Composable
private fun SelectContentPreview() {
    SelectDrawContent()
}