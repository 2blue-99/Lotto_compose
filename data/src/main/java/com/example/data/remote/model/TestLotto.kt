package com.example.data.remote.model

import com.example.domain.model.Lotto
import com.google.gson.annotations.SerializedName

data class LottoResponse(
    @SerializedName("returnValue")
    val responseState: String, // 응답 상태

    @SerializedName("drwNo")
    val drawNumber: Int, // 회차 번호

    @SerializedName("drwNoDate")
    val drawDate: String, // 추첨일

    @SerializedName("totSellamnt")
    val totalSellAmount: Long, // 총 판매 금액

    @SerializedName("firstWinamnt")
    val firstWinTotalAmount: Long, // 1등 총 당첨금

    @SerializedName("firstPrzwnerCo")
    val firstWinCount: Int, // 1등 당첨자 수

    @SerializedName("firstAccumamnt")
    val firstWinPerAmount: Long, // 1등 1명당 당첨금

    val drwtNo1: Int,
    val drwtNo2: Int,
    val drwtNo3: Int,
    val drwtNo4: Int,
    val drwtNo5: Int,
    val drwtNo6: Int,
    val bnusNo: Int,
) {
    fun toDomain(): Lotto {
        return Lotto(
            drawNumber,
            drawDate,
            totalSellAmount,
            firstWinTotalAmount,
            firstWinCount,
            firstWinPerAmount,
            drwtNo1,
            drwtNo2,
            drwtNo3,
            drwtNo4,
            drwtNo5,
            drwtNo6,
            bnusNo
        )
    }
}
