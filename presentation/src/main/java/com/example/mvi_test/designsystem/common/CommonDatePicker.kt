package com.example.mvi_test.designsystem.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.LightGray
import com.example.mvi_test.ui.theme.PrimaryColor
import com.example.mvi_test.util.Utils.targetTimeFormat
import timber.log.Timber
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonDatePicker(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val today = System.currentTimeMillis()
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = today,
        yearRange = 2002..currentYear
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onConfirm) {
                Text(
                    text = "확인",
                    style = CommonStyle.text16Bold,
                    color = PrimaryColor
                )
            }
        },
        dismissButton = {
            TextButton(onConfirm) {
                Text(
                    text = "닫기",
                    style = CommonStyle.text16Bold,
                    color = DarkGray
                )
            }
        }
    ) {
        val todayDate = today.targetTimeFormat()
        val targetDate = datePickerState.selectedDateMillis?.targetTimeFormat() ?: "2025.12.31"
        DatePicker(
            state = datePickerState,
            title = {
                Text(
                    text = "날짜를 선택해주세요.",
                    modifier = Modifier.padding(24.dp)
                )
            },
            headline = {
                Text(
                    text = "$targetDate ~ $todayDate",
                    style = CommonStyle.text20,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            },
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                selectedDayContainerColor = PrimaryColor,
            )
        )
    }
}

@Preview
@Composable
private fun CommonDatePickerPreview() {
    CommonDatePicker({},{})
}