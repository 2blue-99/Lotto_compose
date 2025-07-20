package com.example.mvi_test.screen.home.state

import com.example.domain.util.CommonMessage

sealed class HomeEffectState {
    data class ShowToast(val message: CommonMessage): HomeEffectState()
    data class ShowSnackbar(val message: CommonMessage): HomeEffectState()
}

