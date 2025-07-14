package com.example.mvi_test.screen.statistic.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvi_test.R
import com.example.mvi_test.designsystem.common.CommonExpandableBox
import com.example.mvi_test.designsystem.common.CommonLottoCircle
import com.example.mvi_test.designsystem.common.DynamicHorizontalSelector
import com.example.mvi_test.designsystem.common.VerticalSpacer
import com.example.mvi_test.screen.statistic.StatisticViewModel
import com.example.mvi_test.screen.statistic.state.StatisticActionState
import com.example.mvi_test.screen.statistic.state.StatisticEffectState
import com.example.mvi_test.screen.statistic.state.StatisticUIState
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.PrimaryColor
import com.example.mvi_test.ui.theme.ScreenBackground

@Composable
fun StatisticScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticViewModel = hiltViewModel()
) {
    val statisticUIState by viewModel.statisticUIState.collectAsStateWithLifecycle()

    StatisticScreen(
        statisticUIState = statisticUIState,
        actionHandler = viewModel::actionHandler,
        effectHandler = viewModel::effectHandler
    )
}

@Composable
fun StatisticScreen(
    statisticUIState: StatisticUIState = StatisticUIState.Loading,
    actionHandler: (StatisticActionState) -> Unit,
    effectHandler: (StatisticEffectState) -> Unit,
    modifier: Modifier = Modifier
) {
    var rangePosition by remember { mutableIntStateOf(0) } // 통계 조회 범위

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
                                text = "통계 로또 추첨",
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
            DynamicHorizontalSelector(
                currentPosition = rangePosition,
                onClickTab = { rangePosition = it }
            )
        }
        item {
            StatisticContent()
        }

    }
}

@Preview
@Composable
private fun StatisticScreenPreview() {
    StatisticScreen(
        StatisticUIState.Loading,{},{}
    )
}

@Composable
fun StatisticContent(
    modifier: Modifier = Modifier
) {

    var expand by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .animateContentSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ){
                expand = !expand
            }
        ) {
            Text(
                text = "최근 1개월 추첨 통계",
                style = CommonStyle.text16Bold,
                modifier = modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.Add,
                tint = PrimaryColor,
                contentDescription = "expandable",
            )
        }

        VerticalSpacer(10.dp)

        repeat(3){
            StatisticItem()
        }

        AnimatedVisibility(
            visible = expand,
//            enter = fadeIn(animationSpec = tween(durationMillis = 1000)) + expandVertically(),
//            exit = fadeOut(animationSpec = tween(durationMillis = 300)) + shrinkVertically()
        ) {
            Column {
                repeat(3){
                    StatisticItem()
                }
            }
        }
    }
}

@Preview
@Composable
private fun StatisticContentPreview() {
    StatisticContent()
}

@Composable
fun StatisticItem(
    modifier: Modifier = Modifier,
    onclickItem: (Boolean) -> Unit = {}
) {
    val gradientList = listOf(Color.White, Color(0xFFFFD500))
    val shrinkGradient = Brush.linearGradient(colors = gradientList)

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box { // CheckBox 는 Box 로 싸매야 Preview 에 범위가 나옴
            Checkbox(
                checked = false,
                onCheckedChange = onclickItem,
                colors = CheckboxDefaults.colors(
                    checkedColor = DarkGray,
                    uncheckedColor = DarkGray,
                ),
            )
        }

        CommonLottoCircle(
            targetNumber = "7",
            isAnimation = false,
            modifier = Modifier.size(30.dp)
        )


        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f) // TODO 여기다가 비율 넣으면 됨
                    .height(30.dp)
                    .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                    .background(shrinkGradient)
            )
        }

        Box(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            Text("3번")
        }
    }
}

@Preview
@Composable
private fun StatisticItemPreview() {

}