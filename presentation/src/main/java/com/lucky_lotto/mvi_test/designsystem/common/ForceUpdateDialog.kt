package com.lucky_lotto.mvi_test.designsystem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.window.DialogProperties
import com.lucky_lotto.mvi_test.ui.theme.CommonStyle
import com.lucky_lotto.mvi_test.ui.theme.DarkGray
import com.lucky_lotto.mvi_test.ui.theme.PrimaryColor

@Composable
fun ForceUpdateDialog(
    fetchNote: String,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp),
        ) {
            Text(
                text = "알림",
                style = CommonStyle.text20Bold,
                modifier = Modifier.fillMaxWidth(),
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                VerticalSpacer(10.dp)
                Text(
                    text = "업데이트가 필요해요.",
                    style = CommonStyle.text18,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = fetchNote.ifBlank { "더 나은 서비스를 위해\n최신 버전으로 업데이트 해주세요." },
                    style = CommonStyle.text14,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = DarkGray
                )
            }
            VerticalSpacer(30.dp)
            CommonButton(
                text = "업데이트",
                color = PrimaryColor,
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ForceUpdateDialogPreview() {
    ForceUpdateDialog(
        fetchNote = "- 버그 수정\n- 성능 개선",
        onConfirm = {}
    )
}
