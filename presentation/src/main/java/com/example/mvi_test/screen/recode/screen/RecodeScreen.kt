package com.example.mvi_test.screen.recode.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
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
import com.example.mvi_test.screen.recode.state.RecodeActionState
import com.example.mvi_test.screen.recode.state.RecodeUIState
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.LightGray
import com.example.mvi_test.ui.theme.Red
import com.example.mvi_test.ui.theme.ScreenBackground
import com.example.mvi_test.util.Utils.drawResultToString
import com.example.mvi_test.util.Utils.shareLotto

@Composable
fun RecodeRoute(
    popBackStack: () -> Unit = {},
    modifier: Modifier,
    viewModel: RecodeViewModel = hiltViewModel()
) {
    val recodeUIState by viewModel.recodeUIState.collectAsStateWithLifecycle()

    RecodeScreen(
        recodeUIState = recodeUIState,
        actionHandler = viewModel::actionHandler,
        modifier = modifier
    )
}

@Composable
fun RecodeScreen(
    recodeUIState: RecodeUIState = RecodeUIState.Loading,
    actionHandler: (RecodeActionState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 탑 바
        CommonExpandableBox(
            modifier = Modifier.height(80.dp),
            showQuestion = false,
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

        RecodeContent(
            actionHandler = actionHandler,
            recodeList = if(recodeUIState is RecodeUIState.Success) recodeUIState.lottoRecodeList else emptyList()
        )
    }
}

//@Preview
//@Composable
//private fun RecodeScreen() {
//    RecodeScreen(
//        recodeUIState = RecodeUIState.Loading
//    )
//}

@Composable
fun RecodeContent(
    recodeList: List<LottoRecode> = listOf(LottoRecode(),LottoRecode(),LottoRecode()),
    actionHandler: (RecodeActionState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp),
    ) {
        // 컨트롤 버튼
        Text(
            text = "추첨 기록",
            style = CommonStyle.text16Bold,
            modifier = Modifier.padding(start = 8.dp)
        )

        VerticalSpacer(10.dp)

        AnimatedVisibility(recodeList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.heightIn(min = 200.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 50.dp)
            ) {
                itemsIndexed(recodeList, key = { _, item -> item.saveDate }) { index, lottoRecode ->
                    Column(
                        modifier = Modifier.animateItem()
                    ) {
                        RecodeItem(
                            lottoRecode = lottoRecode,
                            actionHandler = actionHandler
                        )
                        if (index != recodeList.lastIndex) {
                            VerticalSpacer(16.dp)

                            HorizontalDivider(modifier = Modifier.background(LightGray))
                        }
                    }
                }
            }
        }

        AnimatedVisibility(recodeList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "추첨 기록이 비어있어요.",
                    style = CommonStyle.text24Bold,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecodeContentPreview() {
    RecodeContent()
}

@Composable
fun RecodeItem(
    lottoRecode: LottoRecode = LottoRecode(),
    actionHandler: (RecodeActionState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    // 아이템 생성 시 페이드 아웃 -> 인
    var expanded by remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if(expanded) ScreenBackground else Color.White,
        animationSpec = tween(300),
        label = "expanded_item"
    )

    val lottoList = lottoRecode.lottoItem.map { it.drawList }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { expanded = !expanded }
            .background(backgroundColor)
            .padding(10.dp)
    ) {
        Text(
            text = lottoRecode.saveDate,
            style = CommonStyle.text16,
            color = DarkGray
        )

        VerticalSpacer(8.dp)

        lottoRecode.lottoItem.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(2.dp),
            ){
                Box(
                    modifier = Modifier
                        .weight(2f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.sequence,
                        style = CommonStyle.text16,
                        color = DarkGray
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(8f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        CommonLottoAutoRow(
                            lottoItem = item,
                            isAnimation = false
                        )
                        // 총합, 홀짝, 고저 정보
                        VerticalSpacer(4.dp)
                        Text(
                            text = item.toLottoInfo(),
                            style = CommonStyle.text12,
                            color = DarkGray
                        )
                    }
                }
            }
        }

        AnimatedVisibility(expanded) {
            SelectControllerButton(
                onClickDelete = {
                    actionHandler(RecodeActionState.OnClickDelete(saveDate = lottoRecode.saveDate))
                },
                // 복사하기
                onclickCopy = {
                    val text = drawResultToString(lottoList)
                    clipboardManager.setText(AnnotatedString(text))
                },
                // 공유하기
                onClickShare = {
                    val text = drawResultToString(lottoList)
                    context.shareLotto(text)
                }
            )
        }
    }
}

@Preview
@Composable
private fun RecodeItemPreview() {
    RecodeItem()
}

//@Preview
//@Composable
//private fun RecodeContentPreview() {
//    RecodeContent()
//}

@Composable
fun SelectControllerButton(
    onClickDelete: () -> Unit = {},
    onclickCopy: () -> Unit = {},
    onClickShare: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
        ,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CommonButton(
            modifier = Modifier.weight(1f),
            enableColor = Red,
            enabled = true,
            onClick = onClickDelete,
            text = "삭제하기"
        )

        CommonButton(
            modifier = Modifier.weight(1f),
            enabled = true,
            onClick = onclickCopy,
            text = "복사하기"
        )

        CommonButton(
            modifier = Modifier.weight(1f),
            enabled = true,
            onClick = onClickShare,
            text = "공유하기"
        )
    }
}

@Preview
@Composable
private fun SelectControllerButtonPreview() {
    SelectControllerButton()
}