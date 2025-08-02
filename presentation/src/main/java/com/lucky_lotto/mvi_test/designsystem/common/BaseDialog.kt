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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lucky_lotto.domain.type.DialogType
import com.lucky_lotto.mvi_test.ui.theme.CommonStyle
import com.lucky_lotto.mvi_test.ui.theme.DarkGray
import com.lucky_lotto.mvi_test.ui.theme.LightGray
import com.lucky_lotto.mvi_test.ui.theme.PrimaryColor
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
fun BaseDialog(
    dialogType: DialogType,
    autoConfirmDialog: Boolean = false, // 취소, 확인 버튼이 없고, 3초 후 confirm call back 반환
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("")
    var count by remember { mutableDoubleStateOf(2.5) }

    LaunchedEffect(count) {
        if(count == 0.0) { onConfirm() } // 두번 이상 호출되는 버그 유의
        delay(500)
        count-=0.5

    }

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
            if(autoConfirmDialog){
                Text(
                    text = "${count.toInt()}초 후에 광고가 시작됩니다.",
                    style = CommonStyle.text14,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = LightGray
                )
            }else{
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
        DialogType.CAMERA_PERMISSION, false,  {}, {}, {}
    )
}