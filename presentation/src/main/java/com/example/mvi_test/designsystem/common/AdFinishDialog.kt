package com.example.mvi_test.designsystem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.LightGray
import com.example.mvi_test.ui.theme.PrimaryColor
import com.example.mvi_test.ui.theme.ScreenBackground
import com.example.mvi_test.util.AdMobType
import com.google.android.gms.ads.AdSize

@Composable
fun AdFinishDialog(
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
                .padding(16.dp)
        ) {
            VerticalSpacer(20.dp)
            Text(
                text = "종료하시겠습니까?",
                style = CommonStyle.text20Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            VerticalSpacer(30.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ScreenBackground, RoundedCornerShape(8.dp))
                    .heightIn(min = 200.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Ad",
                    style = CommonStyle.text20Bold,
                    color = LightGray
                )
                CommonAdBanner(AdMobType.AdMobDialogBanner())
            }
            VerticalSpacer(30.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CommonButton(
                    text = "네",
                    color = DarkGray,
                    onClick = onCancel,
                    modifier = Modifier.weight(1f).height(50.dp)
                )
                CommonButton(
                    text = "아니오",
                    color = PrimaryColor,
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f).height(50.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun BaseDialogPreview() {
    AdFinishDialog(
        {}, {}, {}
    )
}