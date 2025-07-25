package com.example.mvi_test.screen.home.state

import com.example.domain.model.LottoRound

sealed interface HomeUIState {

    data object Loading : HomeUIState

    data object Fail : HomeUIState

    data class Success(
        val lottoRounds: List<LottoRound>,
        val initPosition: Int? = null // 회차 정보 세팅 인덱스
    ) : HomeUIState
}

