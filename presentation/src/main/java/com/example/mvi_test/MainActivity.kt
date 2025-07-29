package com.example.mvi_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.mvi_test.ui.MyApp
import com.example.mvi_test.ui.theme.MVI_TestTheme
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // 네트워크 상태 관찰
//    @Inject
//    lateinit var networkMonitor: NetworkMonitor

     private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        CoroutineScope(Dispatchers.IO).launch {
            MobileAds.initialize(this@MainActivity) // 애드몹 광고 초기화
        }

        // setContentView 이전에 호출 + light 로 설정
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            )
        )

        setContent {
            // 네트워크 연결 유무 체크
//            val connectedState by viewModel.isConnected.collectAsStateWithLifecycle()
            MVI_TestTheme {
                MyApp()
            }
        }
    }
}