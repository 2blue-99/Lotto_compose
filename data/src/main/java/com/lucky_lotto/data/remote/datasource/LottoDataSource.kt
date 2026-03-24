package com.lucky_lotto.data.remote.datasource

import com.lucky_lotto.data.remote.model.LottoApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoDataSource {
    @GET("/lt645/selectPstLt645InfoNew.do")
    suspend fun requestLottoData(
        @Query("srchDir") searchMethod: String,
        @Query("srchLtEpsd") round: String
        ): Response<LottoApiResponse>
}