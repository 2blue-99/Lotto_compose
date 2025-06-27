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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.example.mvi_test.ui.common.CommonTopBar
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.PrimaryColor
import com.example.mvi_test.ui.theme.SubColor
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
            verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainTopBar()

            LottoPager()

            ButtonLayout(modifier = Modifier.padding(horizontal = 30.dp))
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
private fun MainTopBar(
    onSettingClick: () -> Unit = {},
    onQRClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp)
            .background(Color.White)
    ) {
        Surface(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "행운 로또",
                    style = CommonStyle.text18
                )
            }
            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                Row {
                    IconButton(
                        onClick = onQRClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "QR"
                        )
                    }
                    IconButton(
                        onClick = onSettingClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Setting"
                        )
                    }
                }
            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            HorizontalDivider()
        }
    }
}

@Preview
@Composable
private fun MainTopBarPreview() {
    MainTopBar()
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
fun LottoCardItem(modifier: Modifier = Modifier) {
    
}

@Preview
@Composable
private fun LottoCardItemPreview() {
    LottoCardItem()
}

@Composable
fun ButtonLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Row {
            HomeIconButton(
                containerColor = SubColor,
                outlineColor = Color.DarkGray,
                icon = Icons.Default.Settings,
                title = "랜덤 로또 추첨",
                description = "행운",
                modifier = Modifier.weight(1f),
                onClick = {},
            )
            Spacer(modifier = Modifier.width(16.dp))
            HomeIconButton(
                containerColor = Color.LightGray,
                outlineColor = Color.DarkGray,
                icon = Icons.Default.Create,
                title = "추첨 기록",
                description = "",
                modifier = Modifier.weight(1f),
                onClick = {},
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            HomeIconButton(
                containerColor = PrimaryColor,
                outlineColor = Color.DarkGray,
                icon = Icons.Default.Menu,
                title = "통계 로또 추첨",
                description = "데이터 기반",
                modifier = Modifier.weight(1f),
                onClick = {},
            )
            Spacer(modifier = Modifier.width(16.dp))
            HomeIconButton(
                containerColor = Color.LightGray,
                outlineColor = Color.DarkGray,
                icon = Icons.Default.Star,
                title = "복권 명당",
                description = "",
                modifier = Modifier.weight(1f),
                onClick = {},
            )
        }
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
        border = BorderStroke(0.6.dp, outlineColor),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 30.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "추첨 기록"
            )
            title?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "랜덤 로또 추첨",
                    style = CommonStyle.text18Bold,
                    color = Color.White
                )
            }
            description?.let {
                Spacer(modifier = Modifier.height(4.dp))
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