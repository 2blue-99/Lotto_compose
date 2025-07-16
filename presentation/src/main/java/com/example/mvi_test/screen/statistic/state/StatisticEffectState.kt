package com.example.mvi_test.screen.statistic.state

import com.example.domain.util.CommonMessage
import com.example.mvi_test.screen.random.state.RandomEffectState

sealed class StatisticEffectState {
    data class ShowToast(val message: CommonMessage): StatisticEffectState()
    data class ShowSnackbar(val message: CommonMessage): StatisticEffectState()
}

