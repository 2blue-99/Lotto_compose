package com.example.mvi_test.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mvi_test.R

val pretendard = FontFamily(
    Font(R.font.pretendard_regular)
)

val pretendardBold = FontFamily(
    Font(R.font.pretendard_bold)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

object CommonStyle {
    val text8 = TextStyle(fontFamily = pretendard, fontSize = 8.sp,)
    val text8Bold = TextStyle(fontFamily = pretendardBold, fontSize = 8.sp)

    val text10 = TextStyle(fontFamily = pretendard, fontSize = 10.sp,)
    val text10Bold = TextStyle(fontFamily = pretendardBold, fontSize = 10.sp,)

    val text12 = TextStyle(fontFamily = pretendard, fontSize = 12.sp)
    val text12Bold = TextStyle(fontFamily = pretendardBold, fontSize = 12.sp)

    val text14 = TextStyle(fontFamily = pretendard, fontSize = 14.sp)
    val text14Bold = TextStyle(fontFamily = pretendardBold, fontSize = 14.sp)
    val text14BoldShadow = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 14.sp,
        shadow = Shadow(color = Color.DarkGray, offset = Offset(2f, 2f), blurRadius = 4f)
    )

    val text16 = TextStyle(fontFamily = pretendard, fontSize = 16.sp)
    val text16Bold = TextStyle(fontFamily = pretendardBold, fontSize = 16.sp)
    val text16BoldShadow = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 16.sp,
        shadow = Shadow(color = Color.DarkGray, offset = Offset(2f, 2f), blurRadius = 4f)
    )
    val text18 = TextStyle(fontFamily = pretendard, fontSize = 18.sp)
    val text18Bold = TextStyle(fontFamily = pretendardBold, fontSize = 18.sp)

    val text20 = TextStyle(fontFamily = pretendard, fontSize = 20.sp)
    val text20Bold = TextStyle(fontFamily = pretendardBold, fontSize = 20.sp)
    val text20BoldShadow = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 20.sp,
        shadow = Shadow(color = Color.DarkGray, offset = Offset(2f, 2f), blurRadius = 4f)
    )

    val text22 = TextStyle(fontFamily = pretendard, fontSize = 22.sp)
    val text22Bold = TextStyle(fontFamily = pretendardBold, fontSize = 22.sp)
    val text22BoldShadow = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 22.sp,
        shadow = Shadow(color = Color.DarkGray, offset = Offset(2f, 2f), blurRadius = 4f)
    )

    val text24 = TextStyle(fontFamily = pretendard, fontSize = 24.sp)
    val text24Bold = TextStyle(fontFamily = pretendardBold, fontSize = 24.sp)

    val text30 = TextStyle(fontFamily = pretendard, fontSize = 30.sp)
    val text30Bold = TextStyle(fontFamily = pretendardBold, fontSize = 30.sp)

    val text36 = TextStyle(fontFamily = pretendard, fontSize = 36.sp)
    val text36Bold = TextStyle(fontFamily = pretendardBold, fontSize = 36.sp)

    val text40 = TextStyle(fontFamily = pretendard, fontSize = 40.sp)
    val text40Bold = TextStyle(fontFamily = pretendardBold, fontSize = 40.sp)

    val text50 = TextStyle(fontFamily = pretendard, fontSize = 50.sp)
    val text50Bold = TextStyle(fontFamily = pretendardBold, fontSize = 50.sp)
}
