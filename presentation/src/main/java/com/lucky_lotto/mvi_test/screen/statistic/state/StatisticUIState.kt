package com.lucky_lotto.mvi_test.screen.statistic.state

import com.lucky_lotto.domain.model.StatisticItem

sealed interface StatisticUIState {

    data object Loading : StatisticUIState

    data object Fail : StatisticUIState

    data class Success(
        val statisticItem: List<StatisticItem>
    ) : StatisticUIState
}
