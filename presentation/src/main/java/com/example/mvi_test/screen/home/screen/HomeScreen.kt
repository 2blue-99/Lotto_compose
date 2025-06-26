package com.example.mvi_test.screen.home.screen

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvi_test.screen.home.HomeViewModel
import com.example.mvi_test.screen.home.state.HomeActionState
import com.example.mvi_test.screen.home.state.HomeUIState
import com.example.mvi_test.ui.common.CommonAdBanner
import com.example.mvi_test.ui.theme.CommonStyle
import timber.log.Timber
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    navigateToRandom: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
){
    val uiState by viewModel.lottoState.collectAsStateWithLifecycle()

    HomeScreen(
        navigateToRandom = navigateToRandom,
        navigateToSetting = navigateToSetting,
        uiState = uiState,
        intentHandler = viewModel::intentHandler,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    navigateToRandom: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    uiState: HomeUIState = HomeUIState.Loading,
    intentHandler: (HomeActionState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    when(uiState){
        is HomeUIState.Loading -> Timber.d("Loading")
        is HomeUIState.Success -> Timber.d("Success : ${uiState.lotto}")
        else -> {}
    }
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = navigateToSetting
            ) {
                Icon(imageVector = Icons.Default.Settings, null)
            }

            LottoPager()

            ButtonLayout(modifier = Modifier.padding(horizontal = 10.dp))

            Button(
                onClick = navigateToRandom
            ) {
                Text("번호 추첨하기")
            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            CommonAdBanner()
        }
    }
}

@Preview
@Composable
fun  HomeScreenPreview(){
    HomeScreen(
        uiState = HomeUIState.Loading,
        intentHandler = {}
    )
}

@Composable
fun LottoPager(modifier: Modifier = Modifier) {

    val pagerState = rememberPagerState(pageCount = {1000})

    HorizontalPager(
        modifier = modifier.fillMaxWidth(),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 36.dp)
    ) { page ->
        Card(
            colors =  CardDefaults.cardColors(containerColor = Color.LightGray),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .height(200.dp)
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.3f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    scaleY = lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Text("Hi")
        }
    }

}

@Preview
@Composable
private fun LottoPagerPreview() {
    LottoPager()
}

@Composable
fun ButtonLayout(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
    ) {
        HomeIconButton(
            containerColor = Color.LightGray,
            outlineColor = Color.DarkGray,
            icon = Icons.Default.Settings,
            title = "",
            description = "",
            modifier = Modifier.weight(1f),
            onClick = {},
        )
        Spacer(modifier = Modifier.width(10.dp))
        HomeIconButton(
            containerColor = Color.LightGray,
            outlineColor = Color.DarkGray,
            icon = Icons.Default.Settings,
            title = "",
            description = "",
            modifier = Modifier.weight(1f),
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun ButtonRowPreview() {
    ButtonLayout()
}

@Composable
private fun HomeIconButton(
    containerColor: Color = Color.Gray,
    outlineColor: Color = Color.DarkGray,
    icon: ImageVector = Icons.Default.Settings,
    title: String?,
    description: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        border = BorderStroke(1.dp, outlineColor),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "추첨 기록"
            )
            title?.let {
                Text(
                    text = "랜덤 로또 추첨",
                    style = CommonStyle.text20Bold,
                    color = Color.White
                )
            }
            description?.let {
                Text(
                    text = "행운",
                    style = CommonStyle.text14,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun CommonIconButtonPreview() {
    HomeIconButton(
        title = "title",
        description = "description"
    )
}