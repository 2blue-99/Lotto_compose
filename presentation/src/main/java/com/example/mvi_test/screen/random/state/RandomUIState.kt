package com.example.mvi_test.screen.random.state

sealed interface RandomUIState {

    data object Loading : RandomUIState

    data object Fail : RandomUIState

    data class Success(
        val result: LottoData
    ) : RandomUIState
}

data class LottoData(
    val number: List<List<Int>> = emptyList()
)