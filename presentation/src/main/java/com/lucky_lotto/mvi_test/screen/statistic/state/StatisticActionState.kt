package com.lucky_lotto.mvi_test.screen.statistic.state

import com.lucky_lotto.domain.model.LottoItem
import com.lucky_lotto.domain.type.RangeType

sealed class StatisticActionState {
    data class OnClickRange(val range: RangeType): StatisticActionState()
    data class OnClickDraw(val inputList: List<String>): StatisticActionState()
    data class OnClickSave(val requireNumber: String, val list: List<LottoItem>): StatisticActionState()
    data object OnClickShare : StatisticActionState()
}