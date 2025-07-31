package com.lucky_lotto.mvi_test.screen.recode.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lucky_lotto.domain.model.LottoRecode
import com.lucky_lotto.domain.type.DrawType
import com.lucky_lotto.domain.util.Constants.PADDING_VALUE_AD_BOX
import com.lucky_lotto.mvi_test.R
import com.lucky_lotto.mvi_test.designsystem.common.CommonAnimationButton
import com.lucky_lotto.mvi_test.designsystem.common.CommonExpandableBox
import com.lucky_lotto.mvi_test.designsystem.common.CommonLottoAutoRow
import com.lucky_lotto.mvi_test.designsystem.common.VerticalSpacer
import com.lucky_lotto.mvi_test.screen.recode.RecodeViewModel
import com.lucky_lotto.mvi_test.screen.recode.state.RecodeActionState
import com.lucky_lotto.mvi_test.screen.recode.state.RecodeUIState
import com.lucky_lotto.mvi_test.ui.theme.CommonStyle
import com.lucky_lotto.mvi_test.ui.theme.DarkGray
import com.lucky_lotto.mvi_test.ui.theme.LightGray
import com.lucky_lotto.mvi_test.ui.theme.PrimaryColor
import com.lucky_lotto.mvi_test.ui.theme.Red
import com.lucky_lotto.mvi_test.ui.theme.ScreenBackground
import com.lucky_lotto.mvi_test.ui.theme.SubColor
import com.lucky_lotto.mvi_test.util.Utils.drawResultToString
import com.lucky_lotto.mvi_test.util.Utils.shareLotto

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

@Composable
fun RecodeContent(
    recodeList: List<LottoRecode> = listOf(LottoRecode(),LottoRecode(),LottoRecode()),
    actionHandler: (RecodeActionState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AnimatedContent(recodeList.isEmpty()) { empty ->
            if(!empty){
                LazyColumn(
                    modifier = Modifier.heightIn(min = 200.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(bottom = PADDING_VALUE_AD_BOX.dp)
                ) {
                    itemsIndexed(recodeList, key = { _, item -> item.saveDate }) { index, lottoRecode ->
                        Card (
                            modifier = Modifier
                                .animateItem()
                                .background(Color.White, RoundedCornerShape(16.dp))
                        ) {
                            RecodeItem(
                                lottoRecode = lottoRecode,
                                actionHandler = actionHandler,
                            )
                        }
                    }
                }
            }else{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .height(300.dp),
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
    var expanded by rememberSaveable { mutableStateOf(false) }

    // Column Background Animation 보류
//    val backgroundColor by animateColorAsState(
//        targetValue = if(expanded) ScreenBackground else Color.White,
//        animationSpec = tween(300),
//        label = "expanded_item"
//    )

    val lottoList = lottoRecode.lottoItem.map { it.drawList }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { expanded = !expanded }
            .background(Color.White)
            .padding(16.dp)
    ) {
        RecodeTitle(
            date = lottoRecode.saveDate,
            type = lottoRecode.drawType,
            expanded = expanded
        )
        // 확장 컨텐츠
        AnimatedVisibility(expanded) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                VerticalSpacer(8.dp)

                lottoRecode.lottoItem.forEachIndexed { index, item ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(2.dp),
                        ) {
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
                                CommonLottoAutoRow(
                                    lottoItem = item,
                                    isAnimation = false,
                                )
                            }
                        }
                        VerticalSpacer(4.dp)
                        if (index < lottoRecode.lottoItem.lastIndex) {
                            HorizontalDivider(color = LightGray)
                        }
                        VerticalSpacer(4.dp)
                    }
                }

                SelectControllerButton(
                    onClickDelete = {
                        actionHandler(RecodeActionState.OnClickDelete(saveDate = lottoRecode.saveDate))
                    },
                    // 복사하기
                    onclickCopy = {
                        val text = drawResultToString(lottoRecode.drawType, lottoList, context.packageName)
                        clipboardManager.setText(AnnotatedString(text))
                    },
                    // 공유하기
                    onClickShare = {
                        val text = drawResultToString(lottoRecode.drawType, lottoList, context.packageName)
                        context.shareLotto(text)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecodeItemPreview() {
    RecodeItem()
}

@Composable
fun RecodeTitle(
    date: String,
    type: DrawType,
    expanded: Boolean,
    modifier: Modifier = Modifier
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "rotate"
    )

    val color = if(type is DrawType.StatisticDraw) PrimaryColor else SubColor
    var (infoTitle, info) = when(type){
        is DrawType.LuckyDraw -> { "추첨키워드 : " to type.keyword }
        is DrawType.StatisticDraw -> { "필수 번호 : " to type.list }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = modifier
                    .background(color, RoundedCornerShape(4.dp))
                    .padding(4.dp)
            ) {
                Text(
                    text = type.tagName,
                    style = CommonStyle.text14,
                    color = Color.White,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "저장일 : ",
                    style = CommonStyle.text16Bold,
                    color = DarkGray
                )
                Text(
                    text = date,
                    style = CommonStyle.text16,
                    color = DarkGray
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = infoTitle,
                    style = CommonStyle.text16Bold,
                    color = DarkGray
                )
                Text(
                    text = info,
                    style = CommonStyle.text16,
                    color = DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Icon(
            modifier = Modifier
                .size(40.dp)
                .rotate(rotationAngle),
            painter = painterResource(R.drawable.arrow_down_icon),
            tint = DarkGray,
            contentDescription = "expand"
        )
    }
}

@Preview
@Composable
private fun RecodeTitlePreview() {
    RecodeTitle(
        "2025-07-25",
        DrawType.LuckyDraw(keyword = "행운 7777777777777777777777777777777777777777777777777"),
        false
    )
}

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
        CommonAnimationButton(
            modifier = Modifier.weight(1f),
            enableColor = Red,
            enabled = true,
            onClick = onClickDelete,
            text = "삭제하기"
        )

        CommonAnimationButton(
            modifier = Modifier.weight(1f),
            enabled = true,
            onClick = onclickCopy,
            text = "복사하기"
        )

        CommonAnimationButton(
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