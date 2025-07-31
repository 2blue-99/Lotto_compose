package com.lucky_lotto.mvi_test.screen.random.state

import com.lucky_lotto.domain.model.LottoItem

sealed interface LottoUIState {

    data object Loading : LottoUIState

    data object Fail : LottoUIState

    data class Success(
        /**
         * 5개의 로또 리스트
         * @see com.lucky_lotto.mvi_test.util.Utils.makeLotto
         */
        val lottoList: List<LottoItem>
    ) : LottoUIState
}

