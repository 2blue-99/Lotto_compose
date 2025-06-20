package com.example.mvi_test.screen.random.state

sealed interface ResultUIState {

    data object Loading : ResultUIState

    data object Fail : ResultUIState

    data class Success(
        val result: LottoData
    ) : ResultUIState
}

data class LottoData(
    val number: List<List<Int>> = emptyList()
)