package com.example.mvi_test.screen.recode.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.LottoRecode
import com.example.mvi_test.designsystem.common.CommonButton
import com.example.mvi_test.designsystem.common.CommonExpandableBox
import com.example.mvi_test.designsystem.common.CommonLottoAutoRow
import com.example.mvi_test.designsystem.common.VerticalSpacer
import com.example.mvi_test.screen.recode.RecodeViewModel
import com.example.mvi_test.screen.recode.state.RecodeUIState
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.Red
import com.example.mvi_test.ui.theme.ScreenBackground

@Composable
fun RecodeScreen(
    popBackStack: () -> Unit = {},
    viewModel: RecodeViewModel = hiltViewModel()
) {
    val recodeUIState by viewModel.recodeUIState.collectAsStateWithLifecycle()

    RecodeScreen(
        recodeUIState = recodeUIState
    )
}

@Composable
fun RecodeScreen(
    recodeUIState: RecodeUIState = RecodeUIState.Loading,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 탑 바
        CommonExpandableBox(
            modifier = Modifier.height(80.dp),
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
                            text = "저장 기록",
                            style = CommonStyle.text24Bold,
                            color = Color.White
                        )
                    }
                }
            },
        )

        VerticalSpacer(10.dp)

        RecodeContent()
    }
}

@Preview
@Composable
private fun RecodeScreen() {
    RecodeScreen(
        recodeUIState = RecodeUIState.Loading
    )
}

@Composable
fun RecodeContent(
    onClickDelete: (Int) -> Unit = {},
    recodeList: List<LottoRecode> = emptyList(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(10.dp),
    ) {
        // 컨트롤 버튼
        Text(
            text = "추첨 기록",
            style = CommonStyle.text16Bold
        )

        VerticalSpacer(10.dp)

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(listOf(1,2,3,4,5)){
                RecodeItem()
            }
        }
    }


    // 추첨 기록 텍스트

    // 추첨 리스트
}

@Composable
fun RecodeItem(
    recodeItem: LottoRecode = LottoRecode(),
    modifier: Modifier = Modifier
) {
    // 아이템 생성 시 페이드 아웃 -> 인

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = recodeItem.saveDate,
            style = CommonStyle.text12,
            color = Color.DarkGray
        )
        recodeItem.lottoItem.drawList.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .background(Color.White)
                    .padding(6.dp),
            ){
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = recodeItem.sequence,
                        style = CommonStyle.text16,
                        color = DarkGray
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(8f)
                ) {
                    CommonLottoAutoRow(
                        lottoItem = recodeItem.drawList
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun RecodeContentPreview() {
//    RecodeContent()
//}

@Composable
fun SelectControllerButton(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CommonButton(
            modifier = Modifier.weight(1f),
            enableColor = Red,
            enabled = true,
            onClick = {},
            text = "삭제하기"
        )

        CommonButton(
            modifier = Modifier.weight(1f),
            enabled = true,
            onClick = {},
            text = "복사하기"
        )

        CommonButton(
            modifier = Modifier.weight(1f),
            enabled = true,
            onClick = {},
            text = "공유하기"
        )
    }
}

@Preview
@Composable
private fun SelectControllerButtonPreview() {
    SelectControllerButton()
}