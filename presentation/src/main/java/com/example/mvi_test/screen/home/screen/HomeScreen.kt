package com.example.mvi_test.screen.home.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.Lotto
import com.example.mvi_test.R
import com.example.mvi_test.screen.home.HomeViewModel
import com.example.mvi_test.screen.home.state.HomeActionState
import com.example.mvi_test.screen.home.state.HomeUIState
import com.example.mvi_test.designsystem.common.CommonAdBanner
import com.example.mvi_test.designsystem.common.CommonLottoContent
import com.example.mvi_test.designsystem.common.HorizontalSpacer
import com.example.mvi_test.designsystem.common.VerticalSpacer
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.PrimaryColor
import com.example.mvi_test.ui.theme.Red
import com.example.mvi_test.ui.theme.SubColor
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    navigateToRandom: () -> Unit = {},
    navigateToRecode: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
){
    val uiState by viewModel.homeUIState.collectAsStateWithLifecycle()

    HomeScreen(
        navigateToRandom = navigateToRandom,
        navigateToRecode = navigateToRecode,
        navigateToSetting = navigateToSetting,
        uiState = uiState,
        intentHandler = viewModel::intentHandler,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    navigateToRandom: () -> Unit = {},
    navigateToRecode: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    uiState: HomeUIState = HomeUIState.Loading,
    intentHandler: (HomeActionState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainTopBar(
                onSettingClick = navigateToSetting,
                onQRClick = {}
            )

            VerticalSpacer(10.dp)

            LottoPager(
                lottoList = if(uiState is HomeUIState.Success) uiState.lotto else listOf(Lotto()),
            )

            ButtonLayout(
                navigateToRandom = navigateToRandom,
                navigateToRecode = navigateToRecode,
            )

            VerticalSpacer(10.dp)

            CommonAdBanner(modifier = Modifier.fillMaxSize())
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
            .height(66.dp),
        color = Color.Transparent,
    ) {
        Surface(
            modifier = Modifier.padding(horizontal = 20.dp),
            color = Color.Transparent,
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
                            painter = painterResource(R.drawable.qr_icon),
                            contentDescription = "QR",
                            tint = DarkGray
                        )
                    }
                    IconButton(
                        onClick = onSettingClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Setting",
                            tint = DarkGray
                        )
                    }
                }
            }
        }
//        Box(
//            contentAlignment = Alignment.BottomCenter
//        ) {
//            HorizontalDivider()
//        }
    }
}

@Preview
@Composable
private fun MainTopBarPreview() {
    MainTopBar()
}

@Composable
fun LottoPager(
    lottoList: List<Lotto> = emptyList(),
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { lottoList.size })

    LaunchedEffect(lottoList) {
        pagerState.animateScrollToPage(
            page = lottoList.lastIndex,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        )
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth().zIndex(2f),
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 36.dp)
        ) { page ->
            LottoCardItem(
                lottoItem = lottoList.getOrNull(page) ?: Lotto(),
                modifier = Modifier.graphicsLayer {
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
            )
        }
        LottoInfo(
            lottoItem = lottoList[pagerState.currentPage],
            modifier = Modifier.offset(y = (-20).dp)
        )
    }
}

@Preview
@Composable
private fun LottoPagerPreview() {
    LottoPager(emptyList())
}

@Composable
fun LottoCardItem(
    lottoItem: Lotto = Lotto(),
    modifier: Modifier = Modifier
) {
    val gradient = Brush.linearGradient(colors = listOf(PrimaryColor, SubColor))
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clickable {  }
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(20.dp)
                .padding(top = 10.dp),
        ){
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = lottoItem.drawDate ,
                    style = CommonStyle.text12,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = lottoItem.drawNumber + "회",
                        style = CommonStyle.text36Bold,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                CommonLottoContent(
                    listOf(
                        lottoItem.drwtNo1,
                        lottoItem.drwtNo2,
                        lottoItem.drwtNo3,
                        lottoItem.drwtNo4,
                        lottoItem.drwtNo5,
                        lottoItem.drwtNo6,
                        lottoItem.bnusNo
                    )
                )
            }
//            Box(
//                modifier = Modifier.align(Alignment.TopEnd), // 우상단에 정렬
//            ) {
//                Text(
//                    text = "1억",
//                    style = CommonStyle.text30Bold,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White,
//                )
//            }

        }
    }
}

@Preview
@Composable
private fun LottoCardItemPreview() {
    LottoCardItem()
}

@Composable
fun LottoInfo(
    lottoItem: Lotto = Lotto(),
    modifier: Modifier = Modifier
) {
    val gradient = Brush.verticalGradient(colors = listOf(PrimaryColor, Color.White))

    Surface (
        modifier = modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        tonalElevation = 0.dp,
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .background(gradient)
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .padding(top = 20.dp)
        ) {
            LottoInfoItem(
                titleText = "총 판매금액",
                valueText = lottoItem.totalSellAmount,
                subText = "원"
            )
            HorizontalDivider(color = Color.LightGray)
            LottoInfoItem(
                titleText = "1등 총 당첨금",
                valueText = lottoItem.firstWinTotalAmount,
                subText = "원"
            )
            HorizontalDivider(color = Color.LightGray)
            LottoInfoItem(
                titleText = "1등 당첨자 수",
                valueText = lottoItem.firstWinCount,
                subText = "명"
            )
            HorizontalDivider(color = Color.LightGray)
            LottoInfoItem(
                titleText = "1등 1명당 당첨금",
                valueText = lottoItem.firstWinPerAmount,
                subText = "원"
            )
        }
    }
}

@Preview
@Composable
private fun LottoInfoPreview() {
    LottoInfo()
}

@Composable
fun LottoInfoItem(
    titleText: String = "Title",
    valueText: String = "Value",
    subText: String = "Sub",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 10.dp)
    ) {
        Text(
            text = titleText,
            color = DarkGray,
            style = CommonStyle.text14,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = valueText,
            color = Red,
            style = CommonStyle.text14Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
        HorizontalSpacer(2.dp)
        Text(
            text = subText,
            color = DarkGray,
            style = CommonStyle.text14,
            textAlign = TextAlign.End,
        )
    }
}

@Preview
@Composable
private fun LottoInfoItemPreview() {
    LottoInfoItem()
}



@Composable
fun ButtonLayout(
    navigateToRandom: () -> Unit = {},
    navigateToRecode: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 30.dp)
    ) {
        Row {
            HomeIconButton(
                containerColor = SubColor,
                outlineColor = Color.DarkGray,
                icon = ImageVector.vectorResource(R.drawable.clover_icon),
                titleText = "랜덤 로또 추첨",
                descriptionText = "행운",
                modifier = Modifier.weight(1f),
                onClick = navigateToRandom,
            )
            Spacer(modifier = Modifier.width(24.dp))
            HomeIconButton(
                containerColor = Color.LightGray,
                outlineColor = Color.DarkGray,
//                icon = Icons.Default.Create,
                titleText = "추첨 기록",
                modifier = Modifier.weight(1f),
                onClick = navigateToRecode,
            )
        }
        Spacer(modifier = Modifier.height(26.dp))
        Row {
            HomeIconButton(
                containerColor = PrimaryColor,
                outlineColor = Color.DarkGray,
                icon = ImageVector.vectorResource(R.drawable.statistic_icon),
                titleText = "통계 로또 추첨",
                descriptionText = "데이터 기반",
                modifier = Modifier.weight(1f),
                onClick = {},
            )
            Spacer(modifier = Modifier.width(26.dp))
            HomeIconButton(
                containerColor = Color.LightGray,
                outlineColor = Color.DarkGray,
//                icon = Icons.Default.Star,
                titleText = "복권 명당",
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
    icon: ImageVector? = null,
    titleText: String? = null,
    descriptionText: String? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        elevation = ButtonDefaults.buttonElevation(3.dp),
//        border = BorderStroke(0.6.dp, outlineColor),
        modifier = modifier.aspectRatio(1.2f)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            icon?.let {
                Icon(
                    imageVector = icon,
                    tint = Color.Unspecified,
                    contentDescription = ""
                )
            }
            titleText?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = titleText,
                    style = CommonStyle.text18Bold,
                    color = Color.White
                )
            }
            descriptionText?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = descriptionText,
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
        titleText = "title",
        descriptionText = "description"
    )
}