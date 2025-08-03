package com.lucky_lotto.mvi_test.designsystem.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Composable
fun VerticalSpacer(value: Dp) {
    Spacer(modifier = Modifier.height(value))
}

@Composable
fun HorizontalSpacer(value: Dp) {
    Spacer(modifier = Modifier.width(value))
}

@Composable
fun AutoSizeText(
    text: String,
    style: TextStyle, // Style 의 글자 사이즈는 반영 안되는것같음
    minSize: Int,
    color: Color = TextStyle.Default.color,
    textAlign: TextAlign = TextStyle.Default.textAlign,
    modifier: Modifier = Modifier,
){
    BasicText(
        text = text,
        autoSize = TextAutoSize.StepBased(minFontSize = minSize.sp, maxFontSize = style.fontSize, stepSize = 2.sp),
        style = style.copy(color = color, textAlign = textAlign),
        maxLines = 1,
        modifier = modifier
    )
}