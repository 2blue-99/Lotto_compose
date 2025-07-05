package com.example.mvi_test.screen.random.state

sealed interface LottoUIState {

    data object Loading : LottoUIState

    data object Fail : LottoUIState

    data class Success(
        val lottoList: List<List<Int>>
    ) : LottoUIState
}