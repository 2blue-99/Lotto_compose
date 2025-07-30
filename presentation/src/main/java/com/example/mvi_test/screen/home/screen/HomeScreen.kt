package com.example.mvi_test.screen.home.screen

import android.widget.Toast
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.domain.model.LottoRound
import com.example.domain.model.RoundSpinner
import com.example.domain.util.CommonMessage
import com.example.domain.util.Constants.PADDING_VALUE_AD_BOX
import com.example.mvi_test.R
import com.example.mvi_test.designsystem.common.CommonLottoContent
import com.example.mvi_test.designsystem.common.CommonSpinnerDialog
import com.example.mvi_test.designsystem.common.HorizontalSpacer
import com.example.mvi_test.designsystem.common.VerticalSpacer
import com.example.mvi_test.screen.home.HomeViewModel
import com.example.mvi_test.screen.home.state.BaseDialogState
import com.example.mvi_test.screen.home.state.HomeActionState
import com.example.mvi_test.screen.home.state.HomeEffectState
import com.example.mvi_test.screen.home.state.HomeUIState
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.DarkGray
import com.example.mvi_test.ui.theme.PrimaryColor
import com.example.mvi_test.ui.theme.Red
import com.example.mvi_test.ui.theme.SubColor
import com.example.mvi_test.ui.theme.white50
import com.example.mvi_test.util.baseAnimateScrollToPage
import timber.log.Timber
import kotlin.math.absoluteValue

@Composable
fun HomeRoute(
    navigateToQR: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    navigateToRandom: () -> Unit = {},
    navigateToRecode: () -> Unit = {},
    navigateToStatistic: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val homeUiState by viewModel.homeUIState.collectAsStateWithLifecycle()
    val spinnerDialogState by viewModel.spinnerDialogState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffectState.collect { effect ->
            when(effect){
                is HomeEffectState.ShowToast -> { Toast.makeText(context, effect.message.message, Toast.LENGTH_SHORT).show() }
                is HomeEffectState.ShowSnackbar -> { /*onShowSnackbar(effect.message)*/ }
                is HomeEffectState.NavigateToQR -> navigateToQR()
                is HomeEffectState.NavigateToSetting -> { navigateToSetting() }
                is HomeEffectState.NavigateToRandom -> navigateToRandom()
                is HomeEffectState.NavigateToRecode -> navigateToRecode()
                is HomeEffectState.NavigateToStatistic -> navigateToStatistic()
                else -> {}
            }
        }
    }

    HomeScreen(
        homeUiState = homeUiState,
        spinnerDialogState = spinnerDialogState,
        actionHandler = viewModel::actionHandler,
        effectHandler = viewModel::effectHandler,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    homeUiState: HomeUIState = HomeUIState.Loading,
    spinnerDialogState: BaseDialogState<RoundSpinner> = BaseDialogState.Hide,
    actionHandler: (HomeActionState) -> Unit = {},
    effectHandler: (HomeEffectState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    if(spinnerDialogState is BaseDialogState.Show){
        CommonSpinnerDialog(
            lastIndex = spinnerDialogState.data.lastIndex,
            initIndex = spinnerDialogState.data.initIndex,
            onDismiss = {
                actionHandler(HomeActionState.HideDialog)
            },
            onConfirm = { index ->
                actionHandler(HomeActionState.OnChangeRoundPosition(index))
            }
        )
    }

    Surface(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LazyColumn (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = PADDING_VALUE_AD_BOX.dp)
        ) {
            item {
                MainTopBar(
                    effectHandler = effectHandler,
                )
                VerticalSpacer(10.dp)
            }

            item {
                LottoPager(
                    actionHandler = actionHandler,
                    lottoRoundList = if (homeUiState is HomeUIState.Success) homeUiState.lottoRounds else listOf(
                        LottoRound()
                    ),
                    roundPosition = if (homeUiState is HomeUIState.Success) homeUiState.initPosition else null
                )
            }
            item {
                ButtonLayout(
                    actionHandler = actionHandler,
                    effectHandler = effectHandler,
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        homeUiState = HomeUIState.Loading,
        actionHandler = {}
    )
}

@Composable
private fun MainTopBar(
    effectHandler: (HomeEffectState) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(60.dp)
            .padding(horizontal = 20.dp),
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier =  Modifier.matchParentSize()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.main_logo),
                tint = Color.Unspecified,
                contentDescription = "main_logo",
            )
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.matchParentSize()
        ) {
            Row {
                IconButton(
                    onClick = { effectHandler(HomeEffectState.NavigateToQR) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.qr_icon),
                        contentDescription = "QR",
                        tint = Color.Unspecified
                    )
                }
                IconButton(
                    onClick = { effectHandler(HomeEffectState.NavigateToSetting) }
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
}

@Preview
@Composable
private fun MainTopBarPreview() {
    MainTopBar()
}

@Composable
fun LottoPager(
    actionHandler: (HomeActionState) -> Unit,
    lottoRoundList: List<LottoRound> = emptyList(),
    roundPosition: Int?,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { lottoRoundList.size })

    LaunchedEffect(lottoRoundList) {
        pagerState.baseAnimateScrollToPage(lottoRoundList.lastIndex)
    }

    LaunchedEffect(roundPosition) {
        Timber.d("roundPosition : $roundPosition")
        if(roundPosition != null){
            pagerState.baseAnimateScrollToPage(roundPosition-1) // 페이지 0 부터 시작하는 Index 고려
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(2f),
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 36.dp)
        ) { page ->
            LottoCardItem(
                lottoRoundItem = lottoRoundList.getOrNull(page) ?: LottoRound(),
                onClickDialog = {
                    actionHandler(
                        HomeActionState.ShowDialog(
                            RoundSpinner(
                                lastIndex = lottoRoundList.last().drawNumber.toInt(),
                                initIndex = pagerState.currentPage
                            )
                        )
                    )
                },
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
            lottoRoundItem = lottoRoundList[pagerState.currentPage],
            modifier = Modifier.offset(y = (-20).dp)
        )
    }
}

//@Preview
//@Composable
//private fun LottoPagerPreview() {
//    LottoPager({},emptyList(), 10)
//}

@Composable
fun LottoCardItem(
    lottoRoundItem: LottoRound = LottoRound(),
    onClickDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradient = Brush.linearGradient(colors = listOf(PrimaryColor, SubColor))
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clickable { onClickDialog() }
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(20.dp)
                .padding(top = 10.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = lottoRoundItem.drawDate,
                        style = CommonStyle.text12,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .background(white50, RoundedCornerShape(6.dp))
                            .padding(vertical = 2.dp, horizontal = 10.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            tint = Color.White,
                            contentDescription = "calendar"
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = lottoRoundItem.drawNumber + "회",
                        style = CommonStyle.text36Bold,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                CommonLottoContent(
                    listOf(
                        lottoRoundItem.drwtNo1,
                        lottoRoundItem.drwtNo2,
                        lottoRoundItem.drwtNo3,
                        lottoRoundItem.drwtNo4,
                        lottoRoundItem.drwtNo5,
                        lottoRoundItem.drwtNo6,
                        lottoRoundItem.bnusNo
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
    LottoCardItem(
        onClickDialog = {}
    )
}

@Composable
fun LottoInfo(
    lottoRoundItem: LottoRound = LottoRound(),
    modifier: Modifier = Modifier
) {
    val gradient = Brush.verticalGradient(colors = listOf(PrimaryColor, Color.White))

    Surface(
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
                valueText = lottoRoundItem.totalSellAmount,
                subText = "원"
            )
            HorizontalDivider(color = Color.LightGray)
            LottoInfoItem(
                titleText = "1등 총 당첨금",
                valueText = lottoRoundItem.firstWinTotalAmount,
                subText = "원"
            )
            HorizontalDivider(color = Color.LightGray)
            LottoInfoItem(
                titleText = "1등 당첨자 수",
                valueText = lottoRoundItem.firstWinCount,
                subText = "명"
            )
            HorizontalDivider(color = Color.LightGray)
            LottoInfoItem(
                titleText = "1등 1명당 당첨금",
                valueText = lottoRoundItem.firstWinPerAmount,
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
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
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
    actionHandler: (HomeActionState) -> Unit,
    effectHandler: (HomeEffectState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 30.dp)
    ) {
        Row {
            HomeIconButton(
                containerColor = SubColor,
                icon = ImageVector.vectorResource(R.drawable.clover_icon),
                titleText = "랜덤 로또 추첨",
                descriptionText = "행운",
                modifier = Modifier.weight(1f),
                onClick = { effectHandler(HomeEffectState.NavigateToRandom) },
            )
            Spacer(modifier = Modifier.width(12.dp))
            HomeIconButton(
                containerColor = DarkGray,
                icon = ImageVector.vectorResource(R.drawable.recode_icon),
                titleText = "저장 기록",
                modifier = Modifier.weight(1f),
                onClick = { effectHandler(HomeEffectState.NavigateToRecode) },
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            HomeIconButton(
                containerColor = PrimaryColor,
                icon = ImageVector.vectorResource(R.drawable.statistic_icon),
                titleText = "통계 로또 추첨",
                descriptionText = "데이터 기반",
                modifier = Modifier.weight(1f),
                onClick = { effectHandler(HomeEffectState.NavigateToStatistic) },
            )
            Spacer(modifier = Modifier.width(12.dp))
            HomeIconButton(
                containerColor = Color.LightGray,
//                icon = Icons.Default.Star,
                titleText = "복권 명당",
                modifier = Modifier.weight(1f),
                onClick = { effectHandler(HomeEffectState.ShowToast(CommonMessage.HOME_NOT_READY_YET)) },
            )
        }
    }
}

@Preview
@Composable
private fun ButtonRowPreview() {
    ButtonLayout({},{})
}

@Composable
private fun HomeIconButton(
    containerColor: Color = Color.Gray,
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
            modifier = Modifier
                .padding(vertical = 10.dp)
        ) {
            icon?.let {
                Icon(
                    imageVector = icon,
                    tint = Color.Unspecified,
                    contentDescription = ""
                )
                VerticalSpacer(4.dp)
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