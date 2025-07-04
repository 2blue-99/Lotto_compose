package com.example.mvi_test.screen.random.state

import com.example.domain.model.Keyword

sealed interface KeywordUIState {

    data object Loading : KeywordUIState

    data object Fail : KeywordUIState

    data class Success(
        val keywordList: List<Keyword>
    ) : KeywordUIState
}