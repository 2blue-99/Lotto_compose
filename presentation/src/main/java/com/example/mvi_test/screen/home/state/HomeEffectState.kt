package com.example.mvi_test.screen.home.state

import com.example.domain.model.RoundSpinner
import com.example.domain.util.CommonMessage

sealed class HomeEffectState {
    data class ShowToast(val message: CommonMessage): HomeEffectState()
    data class ShowSnackbar(val message: CommonMessage): HomeEffectState()
    data object NavigateToSetting: HomeEffectState()
    data object NavigateToRandom: HomeEffectState()
    data object NavigateToRecode: HomeEffectState()
    data object NavigateToStatistic: HomeEffectState()
    // TODO 단발성 다이알로그는 이쪽이 맞음
}

