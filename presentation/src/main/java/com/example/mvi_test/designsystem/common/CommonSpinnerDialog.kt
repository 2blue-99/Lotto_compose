package com.example.mvi_test.designsystem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.LightGray
import com.example.mvi_test.ui.theme.PrimaryColor
import kotlinx.coroutines.launch

@Composable
fun CommonSpinnerDialog(
    lastIndex: Int, // 마지막 회차
    initIndex: Int?, // 초기 인덱스
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val roundList = (1..lastIndex).toList()
    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState)
    val coroutineScope = rememberCoroutineScope()

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(initIndex) {
        initIndex?.let {
            lazyListState.scrollToItem(initIndex)
        }
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            val itemHeight = 40.dp
            val visibleItemCount = 5 // 예: 가운데 + 위아래 2개씩
            val padding = itemHeight * (visibleItemCount / 2)
            val visibleItemsMiddle = visibleItemCount / 2

            Text(
                text = "회차 선택",
                style = CommonStyle.text16Bold
            )

            Box {
                LazyColumn(
                    state = lazyListState,
                    flingBehavior = flingBehavior,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight * 5)
                        .fadingEdge(fadingEdgeGradient),
                    contentPadding = PaddingValues(padding)
                ) {
                    itemsIndexed(roundList) { index, number ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            SpinnerItem(
                                number = number.toString(),
                                height = itemHeight
                            )
                        }
                    }
                }
                HorizontalDivider(
                    color = LightGray,
                    modifier = Modifier
                        .padding(horizontal = 50.dp)
                        .offset(y = itemHeight * visibleItemsMiddle)
                )
                HorizontalDivider(
                    color = LightGray,
                    modifier = Modifier
                        .padding(horizontal = 50.dp)
                        .offset(y = itemHeight * (visibleItemsMiddle + 1))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CommonButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    text =  "닫기",
                    onClick = onDismiss
                )
                HorizontalSpacer(10.dp)
                CommonButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    color = PrimaryColor,
                    text =  "확인",
                    onClick = {
                        // 맨 위에 있는 아이템 인덱스
                        val targetIndex = lazyListState.firstVisibleItemIndex
                        // 회전 중이라면 스크롤 중지 처리
                        if(lazyListState.isScrollInProgress){
                            coroutineScope.launch {
                                lazyListState.animateScrollToItem(targetIndex)
                            }
                            // 아이템 선택 처리
                        }else{
                            onConfirm(roundList[targetIndex])
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun SpinnerItem(
    number: String = "7",
    height: Dp = 40.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${number} 회차",
            style = CommonStyle.text16
        )
    }
}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }


@Preview
@Composable
private fun CommonSpinnerDialogPreview() {
    CommonSpinnerDialog(1180,0, {}, {})
}