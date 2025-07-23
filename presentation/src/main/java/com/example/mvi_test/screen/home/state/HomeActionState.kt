package com.example.mvi_test.screen.home.state

import com.example.domain.model.RoundSpinner

sealed class HomeActionState {
    // 추첨 회차 인덱스 변경
    data class OnChangeRoundPosition(val targetRound: Int): HomeActionState()
    data class ShowDialog(val spinnerItem: RoundSpinner): HomeActionState()
    data object HideDialog: HomeActionState()
}