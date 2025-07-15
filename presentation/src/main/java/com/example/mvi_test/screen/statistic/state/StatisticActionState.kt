package com.example.mvi_test.screen.statistic.state

import com.example.domain.type.RangeType

sealed class StatisticActionState {
    data class OnClickRange(val range: RangeType): StatisticActionState()
    data class OnClickDelete(val saveDate: String): StatisticActionState()
    data object OnClickShare : StatisticActionState()
}