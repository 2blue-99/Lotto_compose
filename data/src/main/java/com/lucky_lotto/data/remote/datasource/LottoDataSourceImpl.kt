package com.lucky_lotto.data.remote.datasource

import com.lucky_lotto.data.remote.model.LottoResponse
import com.lucky_lotto.domain.util.APIResponseState
import com.lucky_lotto.data.util.apiErrorHandler
import javax.inject.Inject

class LottoDataSourceImpl @Inject constructor(
    private val lottoRetrofit: LottoDataSource
) {
    suspend fun requestLottoData(round: String): APIResponseState<LottoResponse> {
        return lottoRetrofit.requestLottoData(COMMON_METHOD, round).apiErrorHandler()
    }

    companion object {
        const val COMMON_METHOD = "getLottoNumber"
    }
}