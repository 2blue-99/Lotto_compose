package com.example.data.remote.datasource

import com.example.data.remote.model.LottoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoDataSource {
    @GET("common.do")
    suspend fun requestLottoData(
        @Query("method") method: String,
        @Query("drwNo") round: String
        ): Response<LottoResponse>
}