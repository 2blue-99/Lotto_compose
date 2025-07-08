package com.example.mvi_test.screen.recode.state

import com.example.domain.model.LottoRecode

sealed interface RecodeUIState {

    data object Loading : RecodeUIState

    data object Fail : RecodeUIState

    data class Success(
        val lottoRecodeList: List<LottoRecode>
    ) : RecodeUIState
}
