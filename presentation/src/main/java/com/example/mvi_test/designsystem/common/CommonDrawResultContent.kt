package com.example.mvi_test.designsystem.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.LottoItem
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.LightGray
import com.example.mvi_test.util.DRAW_COMPLETE_TIME
import com.example.mvi_test.util.DRAW_ITEM_SHOW_TIME
import com.example.mvi_test.util.Utils.drawResultToString
import com.example.mvi_test.util.Utils.setAllFalse
import com.example.mvi_test.util.Utils.setAllTrue
import com.example.mvi_test.util.Utils.shareLotto
import com.example.mvi_test.util.Utils.testLottoItem
import com.example.mvi_test.util.Utils.testLottoList
import com.example.mvi_test.util.Utils.toAlphabet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CommonDrawResultContent(
    onClickSave: (List<LottoItem>) -> Unit = {},
    lottoList: List<LottoItem> = testLottoList(),
    mainColor: Color,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
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
                isSaved = false
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
                    targetItem = lottoList[index],
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
    val commonEnabled = if(isDrawCompleted && saveCount != 0) true else false
    val saveText = if(!isDrawCompleted || isSaved) "저장하기" else "${saveCount}개 저장하기"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // 저장하기 버튼
        CommonAnimationButton(
            modifier = Modifier
                .weight(2f)
                .height(50.dp),
            enableColor = mainColor,
            disableColor = LightGray,
            enabled = saveEnabled,
            onClick = {
                val checkedItemList =
                    checkBoxStates.toList().indices.mapIndexedNotNull { index, _ ->
                        if (checkBoxStates[index]) lottoList[index] else null
                    }
                if (checkedItemList.isNotEmpty()) {
                    onClickSave(checkedItemList)
                    isSaved = true
                }
            },
            text = saveText
        )
        // 복사하기
        CommonAnimationButton(
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            enableColor = DarkGray,
            disableColor = LightGray,
            enabled = commonEnabled,
            onClick = {
                val checkedItemList =
                    checkBoxStates.toList().indices.mapIndexedNotNull { index, _ ->
                        if (checkBoxStates[index]) lottoList[index].drawList else null
                    }
                if (checkedItemList.isNotEmpty()) {
                    val text = drawResultToString(checkedItemList)
                    clipboardManager.setText(AnnotatedString(text))
                }
            },
            text = "복사하기"
        )
        // 공유하기
        CommonAnimationButton(
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            enableColor = DarkGray,
            disableColor = LightGray,
            enabled = commonEnabled,
            onClick = {
                val checkedItemList =
                    checkBoxStates.toList().indices.mapIndexedNotNull { index, _ ->
                        if (checkBoxStates[index]) lottoList[index].drawList else null
                    }
                if (checkedItemList.isNotEmpty()) {
                    val text = drawResultToString(checkedItemList)
                    context.shareLotto(text)
                }
            },
            text = "공유하기"
        )
    }
}

@Composable
fun RandomListItem(
    index: Int = 0,
    checkBox: Boolean = false,
    onCheckChange: (Boolean) -> Unit = {},
    targetItem: LottoItem = testLottoItem(),
    modifier: Modifier = Modifier
) {
    // 아이템 생성 시 페이드 아웃 -> 인
    val alpha = remember { Animatable(0f) }
    // targetList 를 CommonLottoAutoRow 에 바로 넣을 경우, 결과가 살짝 보이는 이슈 존재 -> 상태로 관리
    val lottoItem by remember { mutableStateOf(targetItem) }

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
            .clickable { onCheckChange(!checkBox) }
            .padding(vertical = 4.dp),
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
        ) {
            Checkbox(
                checked = checkBox,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = DarkGray,
                    uncheckedColor = DarkGray,
                )
            )
        }
        HorizontalSpacer(6.dp)
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
                lottoItem = lottoItem
            )
        }
    }
}

@Preview
@Composable
private fun RandomListItemPreview() {
    RandomListItem()
}
