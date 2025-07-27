package com.example.mvi_test.designsystem.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.model.LottoItem
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.ScreenBackground
import com.example.mvi_test.util.Utils.testLottoItem
import com.example.mvi_test.util.Utils.toLottoColor
import kotlinx.coroutines.launch

@Composable
fun CommonLottoAutoRow(
    lottoItem: LottoItem = testLottoItem(),
    isAnimation: Boolean = true, // 위로 올라오는 애니메이션 노출 여부
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            lottoItem.drawList.forEachIndexed { index, item ->
                CommonLottoCircle(
                    targetNumber = item,
                    isAnimation = isAnimation,
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(34.dp)
                )
            }
        }
        // 총합, 홀짝, 고저 정보
        VerticalSpacer(6.dp)
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf(
                "총합 ${lottoItem.sum}",
                "홀짝 ${lottoItem.oddEndEvent}",
                "고저 ${lottoItem.highEndLow}"
            ).forEach { info ->
                Box(
                    modifier = Modifier
                        .background(ScreenBackground, RoundedCornerShape(4.dp))
                        .padding(4.dp)
                ) {
                    Text(
                        text = info,
                        style = CommonStyle.text12,
                        color = DarkGray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CommonLottoAutoPreview() {
    CommonLottoAutoRow()
}

@Composable
fun CommonLottoCircle(
    targetNumber: String = "7",
    isAnimation: Boolean = true,
    modifier: Modifier = Modifier
) {
    val offsetY = remember { Animatable( if(isAnimation) 100f else 0f ) }
    val alpha = remember { Animatable(if(isAnimation) 0f else 1f) }

    LaunchedEffect(Unit) {
        if (isAnimation) {
            launch {
                offsetY.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing)
                )
            }
            launch {
                alpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 800)
                )
            }
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer { translationY = offsetY.value }
            .alpha(alpha.value)
            .clip(CircleShape)
            .background(
                targetNumber
                    .toIntOrNull()
                    ?.toLottoColor() ?: Color.White
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = targetNumber,
            style = CommonStyle.text16BoldShadow,
            color = Color.White, // 보너스 로또 구분,
        )
    }
}

@Preview
@Composable
private fun CommonLottoCirclePreview() {
    CommonLottoCircle()
}


//
//@Composable
//fun AutoScrollItem(
//    targetNumber: Int = 45,
//    waitTime: Long = 0,
//    modifier: Modifier = Modifier
//) {
//    var isWait by remember { mutableStateOf(false) }
//    val pagerState = rememberPagerState(pageCount = { 45 })
//    val coroutineScope = rememberCoroutineScope()
//
//    fun scrollPage(currentPage: Int){
//        coroutineScope.launch {
//            pagerState.animateScrollToPage(
//                animationSpec = spring(
//                    dampingRatio = 0.4f,
//                    stiffness = 1000f
//                ),
//                page = currentPage
//            )
//        }
//    }
//
//    // 될때까지 스크롤
//    LaunchedEffect(targetNumber, pagerState.currentPage) {
//        if(!isWait) {
//            delay(waitTime)
//            isWait = true
//        }
//        if(targetNumber > pagerState.currentPage)
//            scrollPage(pagerState.currentPage+1)
//    }
//
//    VerticalPager(
//        state = pagerState,
//        modifier = modifier
//            .height(20.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        userScrollEnabled = false
//    ) { page ->
//        LottoCircle(targetNumber)
//    }
//}
//
//@Preview
//@Composable
//private fun AutoScrollItemPreview() {
//    AutoScrollItem(45)
//}
//
//@Composable
//fun LottoCircle(
//    targetNumber: Int = 77,
//    modifier: Modifier = Modifier
//){
//    Box(
//        modifier = modifier
//            .aspectRatio(1f)
//            .clip(CircleShape)
//            .background(targetNumber.toLottoColor()),
//        contentAlignment = Alignment.Center
//    ){
//        Text(
//            text = targetNumber.toString(),
//            color = Color.White, // 보너스 로또 구분
//            style = CommonStyle.text14
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun LottoCirclePreview() {
//    LottoCircle()
//}