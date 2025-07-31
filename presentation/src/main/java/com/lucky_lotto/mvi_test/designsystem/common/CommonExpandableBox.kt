package com.lucky_lotto.mvi_test.designsystem.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucky_lotto.domain.util.Constants.LUCKY_SCREEN_TITLE_FIRST
import com.lucky_lotto.mvi_test.ui.theme.CommonStyle
import com.lucky_lotto.mvi_test.ui.theme.SubColor
import kotlinx.coroutines.delay

@Composable

fun CommonExpandableBox(
    shrinkColor: List<Color> = listOf(Color(0xFF227DBD), Color(0xFF528B35)),
    expandColor: List<Color> = listOf(Color(0xFFFF8A00), Color(0xFFFFD500)),
    expand: Boolean = false,
    shrinkContent: @Composable () -> Unit,
    expandContent: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (expanded)1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "alpha"
    )
    val shrinkGradient = Brush.linearGradient(colors = shrinkColor)
    val expandGradient = Brush.linearGradient(colors = expandColor)

    // 최초 진입 시 1회 확장
    LaunchedEffect(expand) {
        if(expand){
            delay(LUCKY_SCREEN_TITLE_FIRST)
            expanded = true
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .animateContentSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expanded = !expanded
            }
    ){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(shrinkGradient)
                .matchParentSize()
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .alpha(alpha = alpha)
                .background(shrinkGradient)
                .matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                shrinkContent()

                expandContent?.let {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { expanded = !expanded },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "?",
                            style = CommonStyle.text14Bold,
                            color = SubColor
                        )
                    }
                }
            }
            if(expandContent != null) {
                AnimatedVisibility(visible = expanded) {
                    Box(
                        modifier = Modifier
                            .alpha(alpha)
                            .padding(top = 20.dp)
                    ) {
                        expandContent()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ExpendableBoxPreview() {
    CommonExpandableBox(
        modifier = Modifier,
        shrinkContent = {
            Text("shrinkContent")
        },
        expandContent = {
            Text("expandContent")
        }
    )
}