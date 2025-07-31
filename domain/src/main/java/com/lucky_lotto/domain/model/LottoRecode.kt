package com.lucky_lotto.domain.model

import com.lucky_lotto.domain.type.DrawType
import com.lucky_lotto.domain.type.DrawType.Companion.TYPE_LUCKY


data class LottoRecode(
    val saveDate: String = "2025.06.14 (화) 15:38:48", // 저장일
    val drawType: DrawType = DrawType.LuckyDraw(tagName = TYPE_LUCKY, keyword = "keyword"), // 추첨 타입 (행운, 통계)
    val lottoItem: List<LottoItem> = listOf(LottoItem()),
)