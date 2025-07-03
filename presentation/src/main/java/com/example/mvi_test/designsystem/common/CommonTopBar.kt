package com.example.mvi_test.designsystem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mvi_test.ui.theme.CommonStyle

@Composable
fun CommonTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp)
            .background(Color.White)
            .padding(horizontal = 6.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(

                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "뒤로가기"
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = CommonStyle.text18
            )
        }
    }
}

@Preview
@Composable
private fun CommonTopBarPreview() {
    CommonTopBar(
        "Title"
    )
}