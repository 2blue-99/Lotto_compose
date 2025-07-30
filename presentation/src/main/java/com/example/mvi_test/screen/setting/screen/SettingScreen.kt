package com.example.mvi_test.screen.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.util.Constants.PADDING_VALUE_AD_BOX
import com.example.mvi_test.designsystem.common.HorizontalSpacer
import com.example.mvi_test.designsystem.common.VerticalSpacer
import com.example.mvi_test.screen.setting.SettingViewModel
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.ui.theme.LightGray
import com.example.mvi_test.ui.theme.PrimaryColor
import com.example.mvi_test.ui.theme.ScreenBackground
import com.example.mvi_test.util.openEmail
import com.example.mvi_test.util.openStore
import timber.log.Timber



@Composable
fun SettingRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel()
) {
    Timber.d("settingScreen")

    SettingScreen(
        modifier = modifier
    )
}

@Composable
fun SettingScreen(modifier: Modifier = Modifier) {
    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        contentPadding = PaddingValues(bottom = PADDING_VALUE_AD_BOX.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            VerticalSpacer(20.dp)
        }
        item {
            MemberShipContent()
        }
        item {
            SettingContent()
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen()
}

@Composable
fun MemberShipContent(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.White, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "광고 제거 멤버십은\n추후 업데이트 예정입니다",
            style = CommonStyle.text20Bold,
            color = LightGray,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun MemberShipContentPreview() {
    MemberShipContent()
}

@Composable
fun SettingContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White, RoundedCornerShape(16.dp))
            .heightIn(min = 400.dp)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        SettingItem(
            icon = Icons.Default.Create,
            title = "리뷰 남기기",
            content = "Click",
            contentHighlighting = true,
            onClick = { context.openStore() }
        )
        HorizontalDivider(
            thickness = 0.4.dp,
            color = ScreenBackground
        )
        SettingItem(
            icon = Icons.Default.Email,
            title = "문의하기",
            content = "Click",
            contentHighlighting = true,
            onClick = { context.openEmail() }
        )
        HorizontalDivider(
            thickness = 0.4.dp,
            color = ScreenBackground
        )
        SettingItem(
            icon = Icons.Default.Settings,
            title = "앱 버전",
            content = packageInfo.versionName.toString(),
            contentHighlighting = false,
            onClick = {  }
        )
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    content: String,
    contentHighlighting: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .clickable { onClick.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "icon",
            modifier = Modifier.size(16.dp)
        )
        HorizontalSpacer(6.dp)
        Text(
            text = title,
            style = CommonStyle.text16,

        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = content,
            style = if(contentHighlighting) CommonStyle.text16UnderLine else CommonStyle.text16,
            color = if(contentHighlighting) PrimaryColor else Color.Black
        )
    }
}