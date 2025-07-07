package com.example.mvi_test.screen.random.state

import com.example.domain.model.LottoItem

sealed interface LottoUIState {

    data object Loading : LottoUIState

    data object Fail : LottoUIState

    data class Success(
        /**
         * 5개의 로또 리스트
         * @see com.example.mvi_test.util.Utils.makeLotto
         */
        val lottoList: List<LottoItem>
    ) : LottoUIState
}

