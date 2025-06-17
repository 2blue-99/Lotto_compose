package com.example.mvi_test

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mvi_test.screen.home.MainScreen
import com.example.mvi_test.screen.result.ResultScreen
import com.example.mvi_test.ui.theme.MVI_TestTheme

@Composable
fun MyApp(name: String, modifier: Modifier = Modifier) {
    Scaffold { padding ->
        ResultScreen(Modifier.padding(padding))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MVI_TestTheme {
        MyApp("Android")
    }
}