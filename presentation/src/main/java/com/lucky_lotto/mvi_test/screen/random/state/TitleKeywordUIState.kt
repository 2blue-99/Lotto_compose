package com.lucky_lotto.mvi_test.screen.random.state

import com.lucky_lotto.domain.model.Keyword

sealed interface TitleKeywordUIState {

    data object Loading : TitleKeywordUIState

    data object Fail : TitleKeywordUIState

    data class Success(
        val isFirst: Boolean, // 랜덤 화면 최초 진입인지 여부, 타이틀에서 사용
        val keywordList: List<Keyword>,
    ) : TitleKeywordUIState
}