package com.example.mvi_test.ui.common

import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.util.CommonUtil.toLottoColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CommonLottoAutoScrollRow(
    targetList: List<Int> = listOf(1,5,10,20,30,40,45)
) {
    val waitList = listOf<Long>(0,0,300,600,900,1200,1500)
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        targetList.forEachIndexed { index, number ->
            AutoScrollItem(
                targetNumber = number,
                waitTime = waitList.getOrNull(index) ?: 200L, // 대기시간이 없을 경우, 사양 낮은 기기에서 렉 발생
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
            HorizontalSpacer(6.dp)
        }
    }
}

@Preview
@Composable
private fun CommonLottoAutoScrollPreview() {
    CommonLottoAutoScrollRow()
}

@Composable
fun AutoScrollItem(
    targetNumber: Int = 45,
    waitTime: Long = 0,
    modifier: Modifier = Modifier
) {
    var isWait by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 45 })
    val coroutineScope = rememberCoroutineScope()

    fun scrollPage(currentPage: Int){
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                animationSpec = spring(
                    dampingRatio = 0.4f,
                    stiffness = 1000f
                ),
                page = currentPage
            )
        }
    }

    // 될때까지 스크롤
    LaunchedEffect(targetNumber, pagerState.currentPage) {
        if(!isWait) {
            delay(waitTime)
            isWait = true
        }
        if(targetNumber > pagerState.currentPage)
            scrollPage(pagerState.currentPage+1)
    }

    VerticalPager(
        state = pagerState,
        modifier = modifier
            .height(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = false
    ) { page ->
        LottoCircle(targetNumber)
    }
}

@Preview
@Composable
private fun AutoScrollItemPreview() {
    AutoScrollItem(45)
}

@Composable
fun LottoCircle(
    targetNumber: Int = 77,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(targetNumber.toLottoColor()),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = targetNumber.toString(),
            color = Color.White, // 보너스 로또 구분
            style = CommonStyle.text14
        )
    }
}

@Preview
@Composable
private fun LottoCirclePreview() {
    LottoCircle()
}