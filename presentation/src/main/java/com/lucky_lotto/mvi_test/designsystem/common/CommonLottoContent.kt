package com.lucky_lotto.mvi_test.designsystem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucky_lotto.mvi_test.ui.theme.CommonStyle
import com.lucky_lotto.mvi_test.ui.theme.PrimaryColor

@Composable
fun CommonLottoContent(
    list: List<String> = listOf("1","1","1","1","1","1","1"),
    modifier: Modifier = Modifier
) {
    val entryList = list.toMutableList()
    entryList.add(6, "+")
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        entryList.forEachIndexed { index, number ->
            if(index == 6){
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    imageVector = Icons.Default.Add,
                    tint = Color.White,
                    contentDescription = "plus"
                )
            }else{
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(if(index == 7) PrimaryColor else Color.White), // 보너스 로또 구분
                    contentAlignment = Alignment.Center
                ){
                    AutoSizeText(
                        text = number,
                        style = CommonStyle.text14Bold,
                        minSize = 8,
                        color = if(index == 7) Color.White else PrimaryColor, // 보너스 로또 구분
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CommonLottoContentPreview() {
    CommonLottoContent()
}