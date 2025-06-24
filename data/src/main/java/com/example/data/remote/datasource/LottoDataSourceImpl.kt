package com.example.data.remote.datasource

import com.example.data.remote.model.LottoResponse
import com.example.data.util.APIResponseState
import com.example.data.util.apiErrorHandler
import retrofit2.Response
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