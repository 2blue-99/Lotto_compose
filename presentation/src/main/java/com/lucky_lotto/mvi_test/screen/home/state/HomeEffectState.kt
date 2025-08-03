package com.lucky_lotto.mvi_test.screen.home.state

import com.lucky_lotto.domain.util.CommonMessage

sealed class HomeEffectState {
    data class ShowToast(val message: CommonMessage): HomeEffectState()
    data class ShowSnackbar(val message: CommonMessage): HomeEffectState()
    data object NavigateToQR: HomeEffectState()
    data object NavigateToSetting: HomeEffectState()
    data object NavigateToRandom: HomeEffectState()
    data object NavigateToRecode: HomeEffectState()
    data object NavigateToStatistic: HomeEffectState()
}

