package com.example.mvi_test.screen.random.state

import com.example.domain.util.CommonMessage

sealed class RandomEffectState {
    data class ShowToast(val message: CommonMessage): RandomEffectState()
    data class ShowSnackbar(val message: CommonMessage): RandomEffectState()
}

