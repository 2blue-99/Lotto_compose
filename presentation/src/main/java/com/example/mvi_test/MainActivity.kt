package com.example.mvi_test

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.mvi_test.ui.MyApp
import com.example.mvi_test.ui.theme.MVI_TestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVI_TestTheme {
                BackOnPressed()
                MyApp()
            }
        }
    }

    @Composable
    fun BackOnPressed() {
        val context = LocalContext.current
        var backPressedState by remember { mutableStateOf(true) }
        var backPressedTime = 0L
        val backNoticeMessage = stringResource(R.string.app_back_notice)
        BackHandler(enabled = backPressedState) {
            if (System.currentTimeMillis() - backPressedTime <= 1000L) {
                (context as Activity).finish()
            } else {
                backPressedState = true
                Toast.makeText(context, backNoticeMessage, Toast.LENGTH_SHORT).show()
                backPressedTime = System.currentTimeMillis()
            }
        }
    }
}