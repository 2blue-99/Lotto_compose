package com.example.mvi_test.designsystem.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.LightGray

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    enableColor: Color = DarkGray,
    disableColor: Color = LightGray,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    text: String = "Title"
) {
    val color by animateColorAsState(
        targetValue = if(enabled) enableColor else disableColor,
        animationSpec = tween(300),
        label = "color"
    )

    Button(
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            disabledContainerColor = color
        ),
        contentPadding = PaddingValues(4.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = CommonStyle.text16
        )
    }
}

@Preview
@Composable
private fun CommonButtonPreview() {
    CommonButton()
}