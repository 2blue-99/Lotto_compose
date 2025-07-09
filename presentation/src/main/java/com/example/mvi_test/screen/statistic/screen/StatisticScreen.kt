package com.example.mvi_test.screen.statistic.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvi_test.R
import com.example.mvi_test.designsystem.common.CommonExpandableBox
import com.example.mvi_test.screen.statistic.StatisticViewModel
import com.example.mvi_test.screen.statistic.state.StatisticActionState
import com.example.mvi_test.screen.statistic.state.StatisticEffectState
import com.example.mvi_test.screen.statistic.state.StatisticUIState
import com.example.mvi_test.ui.theme.CommonStyle
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
fun StatisticContent(modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun StatisticContentPreview() {
    StatisticContent()
}