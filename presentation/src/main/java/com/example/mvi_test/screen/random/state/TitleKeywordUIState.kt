package com.example.mvi_test.screen.random.state

import com.example.domain.model.Keyword

sealed interface TitleKeywordUIState {

    data object Loading : TitleKeywordUIState

    data object Fail : TitleKeywordUIState

    data class Success(
        val isFirst: Boolean, // 랜덤 화면 최초 진입인지 여부, 타이틀에서 사용
        val keywordList: List<Keyword>,
    ) : TitleKeywordUIState
}