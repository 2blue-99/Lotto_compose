package com.example.mvi_test.screen.home.state

import com.example.domain.model.Lotto

sealed interface HomeUIState {

    data object Loading : HomeUIState

    data object Fail : HomeUIState

    data class Success(
        val lotto: Lotto
    ) : HomeUIState
}

