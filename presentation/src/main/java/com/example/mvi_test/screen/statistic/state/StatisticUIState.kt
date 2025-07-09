package com.example.mvi_test.screen.statistic.state

import com.example.domain.model.LottoRecode

sealed interface StatisticUIState {

    data object Loading : StatisticUIState

    data object Fail : StatisticUIState

    data class Success(
        val lottoRecodeList: List<LottoRecode>
    ) : StatisticUIState
}
