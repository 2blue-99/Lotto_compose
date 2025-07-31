package com.lucky_lotto.mvi_test.screen.statistic.state

import com.lucky_lotto.domain.util.CommonMessage

sealed class StatisticEffectState {
    data class ShowToast(val message: CommonMessage): StatisticEffectState()
    data class ShowSnackbar(val message: CommonMessage): StatisticEffectState()
}

