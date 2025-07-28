package com.example.mvi_test.designsystem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.PrimaryColor
import com.example.mvi_test.ui.theme.SubColor
import com.google.android.gms.common.internal.service.Common

@Composable
fun BaseDialog(
    dialogInfo: DialogInfo,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog (
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Text(
                text = dialogInfo.title,
                style = CommonStyle.text20Bold,
            )
            VerticalSpacer(26.dp)
            Text(
                text = dialogInfo.content,
                style = CommonStyle.text16,
            )
            VerticalSpacer(26.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                dialogInfo.cancelText?.let {
                    CommonButton(
                        text = dialogInfo.cancelText,
                        color = DarkGray,
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                        )
                }
                CommonButton(
                    text = dialogInfo.confirmText,
                    color = PrimaryColor,
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CommonButton(
    text: String,
    color: Color = DarkGray,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = CommonStyle.text16,
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun CommonButtonPreview() {
    CommonButton("Text", SubColor, {})
}

@Preview
@Composable
private fun BaseDialogPreview() {
    BaseDialog(
        DialogInfo.CAMERA_PERMISSION, {}, {}, {}
    )
}

enum class DialogInfo(
    val title: String,
    val content: String,
    val cancelText: String?, // 취소 버튼 문구
    val confirmText : String, // 확인 버튼 문구
) {
    CAMERA_PERMISSION(
        title = "알림",
        content = "카메라 권한이 필요합니다.\n권한 설정 화면으로 이동하시겠습니까?",
        cancelText = "아니요",
        confirmText = "네"
    )
}