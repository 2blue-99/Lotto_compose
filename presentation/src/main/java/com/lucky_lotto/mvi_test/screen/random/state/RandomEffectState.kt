package com.lucky_lotto.mvi_test.screen.random.state

import com.lucky_lotto.domain.util.CommonMessage

sealed class RandomEffectState {
    data class ShowToast(val message: CommonMessage): RandomEffectState()
    data class ShowSnackbar(val message: CommonMessage): RandomEffectState()
}

