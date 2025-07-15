package com.example.mvi_test.designsystem.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.type.RangeType
import com.example.mvi_test.screen.statistic.state.StatisticActionState
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.LightGray
import kotlinx.coroutines.launch

@Composable
fun DynamicHorizontalSelector(
    actionHandler: (StatisticActionState) -> Unit = {},
    selectorHeight: Dp = 50.dp,
    currentRange: RangeType = RangeType.ONE_YEAR,
    onClickRange: (RangeType) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val alpha = remember { Animatable(1f) }
    val tabs = RangeType.toList()
    val spacing = 4.dp // 탭 간격

    LaunchedEffect(currentRange) {
        launch {
            alpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 100)
            )
        }

        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 100)
        )
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(selectorHeight)
            .clip(RoundedCornerShape(10.dp))
            .background(LightGray)
            .padding(4.dp), // 스프링 애니메이션 잘리는 것을 방지하기 위한 2.dp
    ) {
        val segmentWidth = maxWidth / tabs.size
        val boxWidth = segmentWidth - spacing * 2
        val positions = tabs.indices.map { index ->
            segmentWidth * index + (segmentWidth - boxWidth) / 2
        }
        val animatedOffsetX by animateDpAsState(
            targetValue = positions[currentRange.findIndex()],
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMediumLow
            ),
            label = ""
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterHorizontally)
        ) {
            tabs.forEachIndexed { index, range ->
                Box(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onClickRange(range) }
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = range.monthText,
                        color = Color.DarkGray,
                        style = CommonStyle.text14
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .offset(x = animatedOffsetX)
                .width(boxWidth)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = tabs[currentRange.findIndex()],
                transitionSpec = {
                    fadeIn(tween(200)) togetherWith fadeOut(tween(200))
                },
                label = "rangeIndicator"
            ) { rangeType ->
                Text(
                    text = rangeType.monthText,
                    color = Color.DarkGray,
                    style = CommonStyle.text14,
                )
            }
        }
    }
}

@Preview
@Composable
private fun DynamicHorizontalSelectorPreview() {
    DynamicHorizontalSelector()
}