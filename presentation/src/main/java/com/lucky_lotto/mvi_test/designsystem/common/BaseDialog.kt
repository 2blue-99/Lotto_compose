package com.lucky_lotto.mvi_test.designsystem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lucky_lotto.domain.type.DialogType
import com.lucky_lotto.mvi_test.ui.theme.CommonStyle
import com.lucky_lotto.mvi_test.ui.theme.DarkGray
import com.lucky_lotto.mvi_test.ui.theme.PrimaryColor

@Composable
fun BaseDialog(
    dialogType: DialogType,
    autoConfirmWaitTime: Long? = null, // 자동 종료 카운트
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog (
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp),
        ) {
            Text(
                text = dialogType.title,
                style = CommonStyle.text22Bold,
            )
            Text(
                text = dialogType.content,
                style = CommonStyle.text18,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            // 타이머가 존재할 경우
            autoConfirmWaitTime?.let {
                Text(
                    text = "${(autoConfirmWaitTime/1000).toInt()}초 후에 광고가 시작됩니다.",
                    style = CommonStyle.text14,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = DarkGray
                )
            } ?: run {
                // 일반 다이알로그
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    dialogType.cancelText?.let { text ->
                        CommonButton(
                            text = text,
                            color = DarkGray,
                            onClick = onCancel,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                        )
                    }
                    CommonButton(
                        text = dialogType.confirmText,
                        color = PrimaryColor,
                        onClick = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BaseDialogPreview() {
    BaseDialog(
        DialogType.CAMERA_PERMISSION, 0L,  {}, {}, {}
    )
}